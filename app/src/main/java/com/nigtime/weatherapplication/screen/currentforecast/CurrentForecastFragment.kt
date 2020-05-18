/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.forecast.*
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.settings.UnitOfLength
import com.nigtime.weatherapplication.domain.settings.UnitOfPressure
import com.nigtime.weatherapplication.domain.settings.UnitOfSpeed
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp
import com.nigtime.weatherapplication.domain.utility.UnitFormatHelper
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import com.nigtime.weatherapplication.screen.currentforecast.list.DailyWeatherAdapter
import com.nigtime.weatherapplication.screen.currentforecast.list.HourlyWeatherAdapter
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main.*

/**
 * Главный экран с погодой
 */
class CurrentForecastFragment :
    BaseFragment<CurrentForecastView, CurrentForecastPresenter, CurrentForecastFragment.ParentListener>(
        R.layout.fragment_current_forecast
    ), CurrentForecastView {

    interface ParentListener {
        fun onAddCityClick()
        fun onOpenDrawerClick()
    }

    companion object {
        private const val EXTRA_LOCATION = "weatherapplication.screen.current_forecast.location"

        fun newInstance(location: SavedLocation): CurrentForecastFragment {
            return CurrentForecastFragment().apply {
                arguments = bundleOf(EXTRA_LOCATION to location)
            }
        }
    }

    private val nullScrollListener: NestedScrollView.OnScrollChangeListener? = null
    private var unitFormatHelper: UnitFormatHelper? = null

    override fun getListenerClass(): Class<ParentListener> = ParentListener::class.java

    override fun getPresenterProvider(): PresenterProvider<CurrentForecastPresenter> {
        val location = arguments?.getParcelable<SavedLocation>(EXTRA_LOCATION)
            ?: error("require SavedLocation")

        val vmProvider = ViewModelProvider(this, CurrentForecastPresenterProvider.Factory(location))
        return vmProvider.get(
            location.getKey().toString(),
            CurrentForecastPresenterProvider::class.java
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        unitFormatHelper = UnitFormatHelper(context)
    }

    override fun onDetach() {
        super.onDetach()
        unitFormatHelper = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentForecastScrollView.setOnScrollChangeListener(nullScrollListener)
    }

    override fun onResume() {
        super.onResume()
        Log.d("sas", "onResume [${currentForecastLocationName.text}]")
    }

    override fun onPause() {
        super.onPause()
        Log.d("sas", "onPause [${currentForecastLocationName.text}]")
    }

    private fun initViews() {
        setupDaysSwitcher()
        setupAppBar()
        setupLists()
        setupScrollView()
        setupSwipeToRefresh()
    }

    private fun setupSwipeToRefresh() {
        currentForecastSwipeRefresh.setOnRefreshListener(presenter::onRequestRefresh)
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
                if (it.itemId == R.id.menuFindCity) {
                    parentListener?.onAddCityClick()
                }
                true
            }
            setNavigationOnClickListener {
                parentListener?.onOpenDrawerClick()
            }
        }
    }

    private fun setupLists() {
        currentForecastHourlyList.adapter =
            HourlyWeatherAdapter()
        currentForecastDailyList.apply {
            itemAnimator = null
            adapter =
                DailyWeatherAdapter {
                    showToast("TODO = $it")
                }
        }
    }

    private fun setupScrollView() {
        currentForecastScrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            presenter.onScrollChanged(scrollY)
        }
    }

    override fun setLocationName(name: String) {
        currentForecastLocationName.text = name
    }

    override fun setTimezone(timeZone: String) {
        currentForecastLocalClock.timeZone = timeZone
    }

    override fun showLoadLayout() {
        currentForecastViewSwitcher.switchTo(1, false)
    }

    override fun showErrorLayout() {
        currentForecastViewSwitcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        showToast(R.string.current_forecast_message_error)
    }

    override fun showMainLayout() {
        currentForecastViewSwitcher.switchTo(0, false)
    }

    override fun showClockWidget() {
        TransitionManager.beginDelayedTransition(currentForecastCityInfoLayout)
        currentForecastLocalClock.visibility = View.VISIBLE
    }

    override fun disableRefreshLayout() {
        currentForecastSwipeRefresh.isEnabled = false
    }

    override fun enableRefreshLayout() {
        currentForecastSwipeRefresh.isEnabled = true
    }

    override fun stopRefreshing() {
        currentForecastSwipeRefresh.isRefreshing = false
    }

    override fun selectDaysSwitchButton(buttonId: Int) {
        currentForecastDaysSwitcher.check(buttonId)
    }

    override fun setVerticalScroll(scrollY: Int) {
        currentForecastScrollView.delayScrollY(scrollY)
        liftAppBar(scrollY > 0)
    }

    private fun liftAppBar(doLift: Boolean) {
        currentForecastAppBar.isSelected = doLift
    }

    override fun setCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp) {
        currentForecastCurrentTemp.text = unitFormatHelper?.formatTemp(unitOfTemp, temp)
    }

    override fun setCurrentFeelsLikeTemp(temp: Double, unitOfTemp: UnitOfTemp) {
        currentForecastCurrentFeelsLikeTemp.text =
            unitFormatHelper?.formatFeelsLikeTemp(unitOfTemp, temp)
    }

    override fun setCurrentIco(ico: Int) {
        currentForecastCurrentIco.setImageResource(ico)
    }

    override fun setCurrentWeatherDescription(description: Int) {
        currentForecastCurrentDescription.setText(description)
    }

    override fun setHourlyForecast(
        hourlyWeatherList: List<HourlyForecast.HourlyWeather>,
        unitOfTemp: UnitOfTemp,
        calculateDiffs: Boolean
    ) {
        (currentForecastHourlyList.adapter as HourlyWeatherAdapter).apply {
            setUnitOfTemp(unitOfTemp)
            submitList(hourlyWeatherList, calculateDiffs)
        }
    }

    override fun setDailyForecast(
        dailyWeather: List<DailyForecast.DailyWeather>,
        unitOfTemp: UnitOfTemp,
        calculateDiffs: Boolean
    ) {
        (currentForecastDailyList.adapter as DailyWeatherAdapter).apply {
            setUnitOfTemp(unitOfTemp)
            submitList(dailyWeather, calculateDiffs)
        }
    }

    override fun setWind(wind: Wind, unitOfSpeed: UnitOfSpeed) {
        currentForecastWindIndicator.rotation = wind.degrees.toFloat()
        currentForecastWindSpeed.text = unitFormatHelper?.formatSpeed(unitOfSpeed, wind.speed)
        currentForecastWindDirChars.setText(wind.cardinalDirection.getAbbreviatedName())
    }

    override fun setHumidity(humidity: Int) {
        currentForecastHumidity.text = unitFormatHelper?.formatHumidity(humidity)
    }

    override fun setPressure(pressure: Double, unitOfPressure: UnitOfPressure) {
        currentForecastPressure.text = unitFormatHelper?.formatPressure(unitOfPressure, pressure)
    }

    override fun setPrecipitation(probabilityOfPrecipitation: HourlyForecast.ProbabilityOfPrecipitation) {
        currentForecastPrecipitation3Hours.text =
            unitFormatHelper?.formatProbabilityOfPrecipitation(probabilityOfPrecipitation.next3Hours)
        currentForecastPrecipitation6Hours.text =
            unitFormatHelper?.formatProbabilityOfPrecipitation(probabilityOfPrecipitation.next6Hours)
        currentForecastPrecipitation12Hours.text =
            unitFormatHelper?.formatProbabilityOfPrecipitation(probabilityOfPrecipitation.next12Hours)
    }

    override fun setVisibility(visibility: Double, unitOfLength: UnitOfLength) {
        currentForecastVisibility.text =
            unitFormatHelper?.formatVisibility(unitOfLength, visibility)
    }

    override fun setAirQuality(airQuality: AirQuality) {
        currentForecastAirQuality.text = unitFormatHelper?.formatAirQuality(airQuality)
    }

    override fun setUvIndex(uvIndex: UvIndex) {
        currentForecastUvIndex.text = unitFormatHelper?.formatUvIndex(uvIndex)
    }

    override fun setClouds(clouds: Int) {
        currentForecastClouds.text = unitFormatHelper?.formatCloudsCoverage(clouds)
    }

    override fun setSunInfo(sunInfo: SunInfo) {
        //TODO сделать
    }
}
