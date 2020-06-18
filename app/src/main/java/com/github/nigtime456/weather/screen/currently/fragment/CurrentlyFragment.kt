/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.github.nigtime456.weather.screen.currently.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.forecast.HourlyForecast
import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.settings.UnitOfLength
import com.github.nigtime456.weather.data.settings.UnitOfPressure
import com.github.nigtime456.weather.data.settings.UnitOfSpeed
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.data.weather.WeatherUtils
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.base.BaseFragment
import com.github.nigtime456.weather.screen.currently.fragment.di.CurrentlyFragmentModule
import com.github.nigtime456.weather.screen.currently.fragment.di.DaggerCurrentlyFragmentComponent
import com.github.nigtime456.weather.screen.currently.fragment.list.DailyForecastModel
import com.github.nigtime456.weather.screen.currently.fragment.list.HourlyForecastModel
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_currently.*
import kotlinx.android.synthetic.main.fragment_currently_body.*
import javax.inject.Inject

/**
 * Главный экран с погодой
 */
class CurrentlyFragment : BaseFragment<CurrentlyFragment.ParentActivity>(),
    CurrentlyFragmentContract.View,
    FlexibleAdapter.OnItemClickListener {

    interface ParentActivity {
        fun setLocationName(location: SavedLocation)
        fun setTimeZone(timeZone: String)
        fun showDailyForecastScreen(
            location: SavedLocation,
            partOfDay: PartOfDay,
            dayIndex: Int
        )

        fun setPartOfDay(partOfDay: PartOfDay)
    }

    companion object {
        private const val EXTRA_LOCATION = "weatherapplication.currently.fragment.location"

        fun newInstance(location: SavedLocation): CurrentlyFragment {
            return CurrentlyFragment()
                .apply {
                    arguments = bundleOf(EXTRA_LOCATION to location)
                }
        }
    }

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: CurrentlyFragmentContract.Presenter

    private val hourlyForecastAdapter: FlexibleAdapter<HourlyForecastModel> = FlexibleAdapter(null)
    private val dailyForecastAdapter: FlexibleAdapter<DailyForecastModel> =
        FlexibleAdapter(null, this)

    //</editor-fold>

    override fun getLayoutResId(): Int {
        return R.layout.fragment_currently
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.provideForecast()
        presenter.onViewForeground()
    }

    //</editor-fold>

    //<editor-fold desc = "initialization">
    override fun getListenerClass(): Class<ParentActivity> = ParentActivity::class.java

    override fun initDi(appComponent: AppComponent) {
        val currentLocation = getLocation()
        DaggerCurrentlyFragmentComponent.builder()
            .appComponent(appComponent)
            .currentlyFragmentModule(CurrentlyFragmentModule(this, currentLocation))
            .build()
            .inject(this)
    }

    private fun getLocation(): SavedLocation {
        return arguments?.getParcelable(EXTRA_LOCATION) ?: error("require location")
    }

    private fun initViews() {
        initPullToRefresh()
        initList()
    }

    private fun initList() {
        fragment_currently_hourly_recycler.apply {
            setHasFixedSize(true)
            adapter = hourlyForecastAdapter
        }
        fragment_currently_daily_recycler.apply {
            setHasFixedSize(true)
            adapter = dailyForecastAdapter
        }
    }

    private fun initPullToRefresh() {
        fragment_currently_refresh_layout.setOnRefreshListener {
            presenter.refreshData()
        }
    }

    //</editor-fold>

    //<editor-fold desc = "ui events">
    override fun onItemClick(view: View, position: Int): Boolean {
        if (view.id == R.id.item_daily_forecast_root) {
            presenter.clickDailyForecast(position)
        }
        return false
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun setLocationName(location: SavedLocation) {
        listener?.setLocationName(location)
    }

    override fun showLoadLayout() {
        fragment_currently_view_switcher.switchTo(1, true)
    }

    override fun showErrorLayout() {
        fragment_currently_view_switcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        Toast.makeText(requireContext(), R.string.connection_error, Toast.LENGTH_LONG)
            .show()
    }

    override fun showMainLayout() {
        fragment_currently_view_switcher.switchTo(0, true)
    }

    override fun showDailyForecastScreen(
        location: SavedLocation,
        partOfDay: PartOfDay,
        dayIndex: Int
    ) {
        listener?.showDailyForecastScreen(location, partOfDay, dayIndex)
    }

    override fun disableRefreshLayout() {
        fragment_currently_refresh_layout.isEnabled = false
    }

    override fun enableRefreshLayout() {
        fragment_currently_refresh_layout.isEnabled = true
    }

    override fun stopRefreshing(success: Boolean) {
        fragment_currently_refresh_layout.finishRefresh(success)
    }

    override fun setUpdateTime(time: Long) {
        fragment_currently_refresh_header.setUpdateTime(time)
    }

    override fun setPartOfDay(partOfDay: PartOfDay) {
        listener?.setPartOfDay(partOfDay)
    }

    override fun setTimeZone(timeZone: String) {
        listener?.setTimeZone(timeZone)
    }

    override fun showCurrentlyWeather(
        temp: Double,
        apparentTemp: Double,
        weatherCode: String,
        unitOfTemp: UnitOfTemp
    ) {
        fragment_currently_temp.text = WeatherUtils.formatTemp(requireContext(), unitOfTemp, temp)
        fragment_currently_apparent_temp.text =
            WeatherUtils.formatApparentTemp(requireContext(), unitOfTemp, apparentTemp)
        fragment_currently_ico.setImageResource(WeatherUtils.getWeatherIcon(weatherCode))
        fragment_currently_description.text =
            WeatherUtils.getWeatherDescription(requireContext(), weatherCode)
    }

    override fun showWind(degrees: Int, speed: Double, gust: Double, unitOfSpeed: UnitOfSpeed) {
        fragment_currently_wind_dir.text =
            WeatherUtils.getAbbreviatedDirection(requireContext(), degrees)
        fragment_currently_wind_speed.text =
            WeatherUtils.formatSpeed(requireContext(), unitOfSpeed, speed)
        fragment_currently_wind_gust.text =
            WeatherUtils.formatSpeed(requireContext(), unitOfSpeed, gust)
    }

    override fun showHumidity(humidity: Double) {
        fragment_currently_humidity.text = WeatherUtils.formatHumidity(requireContext(), humidity)
    }

    override fun showUvIndex(index: Int) {
        fragment_currently_uv_index.text =
            WeatherUtils.getUvIndexDescription(requireContext(), index)
    }

    override fun showPressure(pressure: Double, unitOfPressure: UnitOfPressure) {
        fragment_currently_pressure.text =
            WeatherUtils.formatPressure(requireContext(), unitOfPressure, pressure)
    }

    override fun showVisibility(visibility: Double, unitOfLength: UnitOfLength) {
        fragment_currently_visibility.text =
            WeatherUtils.formatLength(requireContext(), unitOfLength, visibility)
    }

    override fun showDewPoint(dewPoint: Double, unitOfTemp: UnitOfTemp) {
        fragment_currently_dew_point.text =
            WeatherUtils.formatTemp(requireContext(), unitOfTemp, dewPoint)
    }

    override fun showCloudsCoverage(clouds: Double) {
        fragment_currently_clouds_coverage.text =
            WeatherUtils.formatCloudsCoverage(requireContext(), clouds)
    }

    override fun showHourlyForecast(
        forecast: List<HourlyForecast>,
        unitOfTemp: UnitOfTemp,
        timeZone: String
    ) {
        hourlyForecastAdapter.updateDataSet(
            HourlyForecastModel.mapList(
                forecast,
                unitOfTemp,
                timeZone
            )
        )
    }

    override fun showDailyForecast(forecast: List<DailyForecast>, unitOfTemp: UnitOfTemp) {
        dailyForecastAdapter.updateDataSet(DailyForecastModel.mapList(forecast, unitOfTemp))
    }

    //</editor-fold>

}
