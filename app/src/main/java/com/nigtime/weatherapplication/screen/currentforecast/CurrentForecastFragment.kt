/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.forecast.*
import com.nigtime.weatherapplication.domain.settings.UnitFormatter
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.PresenterFactory
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main_current.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main_daily.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main_environment.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main_hourly.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main_more.*
import kotlinx.android.synthetic.main.fragment_current_forecast_precipitation.*

/**
 * Главный экран с погодой
 */
class CurrentForecastFragment :
    BaseFragment<CurrentForecastView, CurrentForecastPresenter, CurrentForecastFragment.ParentListener>(
        R.layout.fragment_current_forecast
    ), CurrentForecastView {

    interface ParentListener {
        fun onClickAddCity()
        fun onClickOpenDrawer()
    }

    companion object {
        //TODO constant
        private const val EXTRA_CITY = "com.nigtime.weatherapplication.screens.currentforecast.CITY"

        fun newInstance(cityForForecast: CityForForecast): CurrentForecastFragment {
            return CurrentForecastFragment().apply {
                arguments = bundleOf(EXTRA_CITY to cityForForecast)
            }
        }
    }

    private var unitFormatter: UnitFormatter? = null
    private var currentCity = lazy { getCurrentCity() }
    private val nullScrollListener: NestedScrollView.OnScrollChangeListener? = null

    @Suppress("RemoveExplicitTypeArguments")
    private fun getCurrentCity(): CityForForecast {
        return arguments.let { args -> args?.getParcelable<CityForForecast>(EXTRA_CITY) }
            ?: error("required CityForForecast")
    }

    override fun getListenerClass(): Class<ParentListener> = ParentListener::class.java

    override fun getPresenterFactory(): PresenterFactory<CurrentForecastPresenter> {
        val key = currentCity.value.cityId.toString()
        return ViewModelProvider(this).get(key, CurrentForecastPresenterFactory::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("sas","create = ${hashCode()} ${currentCity.value.cityName}")
        unitFormatter = App.INSTANCE.appContainer.settingsManager.getUnitFormatter()
        initViews()
        presenter.provideForecast(currentCity.value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unitFormatter = null
        currentForecastScrollView.setOnScrollChangeListener(nullScrollListener)
    }

    private fun initViews() {
        setupDaysSwitcher()
        setupAppBar()
        setupLists()
        setupScrollView()
        setupSwipeToRefresh()
    }

    private fun setupSwipeToRefresh() {
        currentForecastSwipeRefresh.setOnRefreshListener {
            currentForecastSwipeRefresh.isRefreshing = false
            showToast("TODO!!")
        }
    }

    private fun setupDaysSwitcher() {
        currentForecastDaysSwitcher.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                presenter.onDisplayedDaysSwitched(checkedId)
            }
        }
    }

    private fun setupAppBar() {
        currentForecastToolbar.apply {
            setOnMenuItemClickListener {
                if (it.itemId == R.id.menuAdd) {
                    parentListener?.onClickAddCity()
                }
                true
            }
            setNavigationOnClickListener {
                parentListener?.onClickOpenDrawer()
            }
        }
    }

    private fun setupLists() {
        currentForecastHourlyList.adapter = HourlyWeatherAdapter(unitFormatter!!)
        currentForecastDailyList.apply {
            itemAnimator = null
            adapter = DailyWeatherAdapter(unitFormatter!!) {
                showToast("TODO = $it")
            }
        }
    }

    private fun setupScrollView() {
        currentForecastScrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            presenter.onScrollChanged(scrollY)
        }
    }

    override fun setCityName(cityName: String) {
        currentForecastCityName.text = cityName
    }

    override fun showLoadLayout() {
        currentForecastViewSwitcher.switchTo(1, false)
    }

    override fun showErrorLayout() {
        currentForecastViewSwitcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        showToast("Error!")
    }

    override fun showMainLayout() {
        currentForecastViewSwitcher.switchTo(0, false)
    }

    override fun setCurrentTemp(temp: Double) {
        currentForecastCurrentTemp.text = unitFormatter!!.formatTemp(temp)
    }

    override fun setCurrentFeelsLikeTemp(temp: Double) {
        currentForecastCurrentFeelsLikeTemp.text = unitFormatter!!.formatFeelsLikeTemp(temp)
    }

    override fun setCurrentIco(@DrawableRes ico: Int) {
        currentForecastCurrentIco.setImageResource(ico)
    }

    override fun setCurrentWeatherDescription(@StringRes description: Int) {
        currentForecastCurrentDescription.setText(description)
    }

    override fun setHourlyForecast(hourlyWeatherList: List<HourlyForecast.HourlyWeather>) {
        (currentForecastHourlyList.adapter as HourlyWeatherAdapter).submitList(hourlyWeatherList)
    }

    override fun setDailyForecast(dailyWeather: List<DailyForecast.DailyWeather>) {
        (currentForecastDailyList.adapter as DailyWeatherAdapter).submitList(dailyWeather)
    }

    override fun selectDaysSwitchButton(buttonId: Int) {
        currentForecastDaysSwitcher.check(buttonId)
    }

    override fun setVerticalScroll(scrollY: Int) {
        currentForecastScrollView.delayScrollY(scrollY)
        //TODO может вынести в презентер ?
        liftAppBar(scrollY > 0)
    }

    private fun liftAppBar(doLift: Boolean) {
        currentForecastAppBar.isSelected = doLift
    }

    override fun setWind(wind: Wind) {
        currentForecastWindIndicator.rotation = wind.degrees.toFloat()
        currentForecastWindSpeed.text = unitFormatter!!.formatSpeed(wind.speed)
        currentForecastWindDirChars.setText(wind.cardinalDirection.getAbbreviatedName())
    }

    override fun setHumidity(humidity: Int) {
        currentForecastHumidity.text = unitFormatter!!.formatHumidity(humidity)
    }


    override fun setPressure(pressure: Double) {
        currentForecastPressure.text = unitFormatter!!.formatPressure(pressure)
    }

    override fun setPrecipitation(probabilityOfPrecipitation: HourlyForecast.ProbabilityOfPrecipitation) {
        currentForecastPrecipitation3Hours.text =
            unitFormatter!!.formatProbabilityOfPrecipitation(probabilityOfPrecipitation.next3Hours)
        currentForecastPrecipitation6Hours.text =
            unitFormatter!!.formatProbabilityOfPrecipitation(probabilityOfPrecipitation.next6Hours)
        currentForecastPrecipitation12Hours.text =
            unitFormatter!!.formatProbabilityOfPrecipitation(probabilityOfPrecipitation.next12Hours)
    }

    override fun setVisibility(visibility: Double) {
        fragmentCurrentForecastVisibility.text = unitFormatter!!.formatVisibility(visibility)
    }

    override fun setAirQuality(airQuality: AirQuality) {
        fragmentCurrentForecastAirQuality.text = unitFormatter!!.formatAirQuality(airQuality)
    }

    override fun setUvIndex(uvIndex: UvIndex) {
        fragmentCurrentForecastUvIndex.text = unitFormatter!!.formatUvIndex(uvIndex)
    }

    override fun setClouds(clouds: Int) {
        fragmentCurrentForecastClouds.text = unitFormatter!!.formatCloudsCoverage(clouds)
    }


    override fun showClockWidget() {
        TransitionManager.beginDelayedTransition(currentForecastCityInfoLayout)
        currentForecastLocalClock.visibility = View.VISIBLE
    }

    override fun setTimezone(timeZone: String) {
        currentForecastLocalClock.timeZone = timeZone
    }

    override fun setSunInfo(sunInfo: SunInfo) {
        Log.d("sas", "sunInfo = $sunInfo")
    }


}
