/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.settings.UnitOfLength
import com.github.nigtime456.weather.data.settings.UnitOfPressure
import com.github.nigtime456.weather.data.settings.UnitOfSpeed
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.data.weather.WeatherUtils
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.base.BaseFragment
import com.github.nigtime456.weather.screen.daily.fragment.di.DaggerDailyFragmentComponent
import com.github.nigtime456.weather.screen.daily.fragment.di.DailyFragmentModule
import com.github.nigtime456.weather.utils.ui.DateFormatters
import kotlinx.android.synthetic.main.fragment_daily.*
import javax.inject.Inject

class DailyFragment : BaseFragment<Nothing>(), DailyFragmentContract.View {

    companion object {
        private const val EXTRA_FORECAST = "weatherapplication.daily.fragment.forecast"
        fun newInstance(forecast: DailyForecast) =
            DailyFragment().apply {
                arguments = bundleOf(EXTRA_FORECAST to forecast)
            }
    }

    //<editor-fold desc = "fields">
    @Inject
    lateinit var presenter: DailyFragmentContract.Presenter

    //</editor-fold>

    //<editor-fold desc = "lifecycle">
    override fun getLayoutResId(): Int {
        return R.layout.fragment_daily
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    //</editor-fold>

    //<editor-fold desc = "initialization"
    override fun initDi(appComponent: AppComponent) {
        DaggerDailyFragmentComponent.builder()
            .appComponent(appComponent)
            .dailyFragmentModule(DailyFragmentModule(this, getForecast()))
            .build()
            .inject(this)
    }

    private fun getForecast(): DailyForecast {
        return arguments?.getParcelable(EXTRA_FORECAST) ?: error("require forecast")
    }
    //</editor-fold>

    //<editor-fold desc = "MVP">
    override fun showWeather(
        weatherCode: String,
        highTemp: Double,
        lowTemp: Double,
        unitOfTemp: UnitOfTemp
    ) {
        fragment_daily_ico.setImageResource(WeatherUtils.getWeatherIcon(weatherCode))
        fragment_daily_description.text =
            WeatherUtils.getWeatherDescription(requireContext(), weatherCode)
        fragment_daily_high_temp.text =
            WeatherUtils.formatTemp(requireContext(), unitOfTemp, highTemp)
        fragment_daily_low_temp.text =
            WeatherUtils.formatTemp(requireContext(), unitOfTemp, lowTemp)
    }

    override fun showWind(degrees: Int, speed: Double, gust: Double, unitOfSpeed: UnitOfSpeed) {
        fragment_daily_wind_dir.text =
            WeatherUtils.getAbbreviatedDirection(requireContext(), degrees)
        fragment_daily_wind_speed.text =
            WeatherUtils.formatSpeed(requireContext(), unitOfSpeed, speed)
        fragment_daily_wind_gust.text =
            WeatherUtils.formatSpeed(requireContext(), unitOfSpeed, gust)
    }

    override fun showHumidity(humidity: Double) {
        fragment_daily_humidity.text = WeatherUtils.formatHumidity(requireContext(), humidity)
    }

    override fun showUvIndex(index: Int) {
        fragment_daily_uv_index.text = WeatherUtils.getUvIndexDescription(requireContext(), index)
    }

    override fun showPressure(pressure: Double, unitOfPressure: UnitOfPressure) {
        fragment_daily_pressure.text =
            WeatherUtils.formatPressure(requireContext(), unitOfPressure, pressure)
    }

    override fun showVisibility(visibility: Double, unitOfLength: UnitOfLength) {
        fragment_daily_visibility.text =
            WeatherUtils.formatLength(requireContext(), unitOfLength, visibility)
    }

    override fun showDewPoint(dewPoint: Double, unitOfTemp: UnitOfTemp) {
        fragment_daily_dew_point.text =
            WeatherUtils.formatTemp(requireContext(), unitOfTemp, dewPoint)
    }

    override fun showCloudsCoverage(clouds: Double) {
        fragment_daily_clouds_coverage.text =
            WeatherUtils.formatCloudsCoverage(requireContext(), clouds)
    }

    override fun showMoonPhase(phase: Double) {
        fragment_daily_moon_phase_ico.setImageResource(WeatherUtils.getMoonIco(phase))
        fragment_daily_moon_phase.text = WeatherUtils.getMoonDescription(requireContext(), phase)
    }

    override fun showSunriseAndSunset(sunrise: Long, sunset: Long, timeZone: String) {
        val formatter = DateFormatters.getHoursFormatter(requireContext(), timeZone)
        fragment_daily_sunrise.text = formatter.format(sunrise)
        fragment_daily_sunset.text = formatter.format(sunset)
    }

    //</editor-fold>
}