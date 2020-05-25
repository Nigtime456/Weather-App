/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.screen.currentforecast

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.util.ThemeUtils
import com.gmail.nigtime456.weatherapplication.common.util.list.ColorDividerDecoration
import com.gmail.nigtime456.weatherapplication.domain.forecast.*
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.domain.util.UnitFormatHelper
import com.gmail.nigtime456.weatherapplication.screen.common.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.currentforecast.list.DailyWeatherAdapter
import com.gmail.nigtime456.weatherapplication.screen.currentforecast.list.HourlyWeatherAdapter
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main.*

/**
 * Главный экран с погодой
 */
class CurrentForecastFragment :
    BaseFragment<CurrentForecastView, CurrentForecastPresenter, CurrentForecastFragment.ParentListener>(
        R.layout.fragment_current_forecast
    ),
    CurrentForecastView {

    interface ParentListener {
        fun onAddCityClick()
        fun onOpenDrawerClick()
        fun navigateToDailyPagesFragment(location: SavedLocation, dayIndex: Int)
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

    override fun getPresenterProvider(): BasePresenterProvider<CurrentForecastPresenter> {
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
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
        currentForecastSwipeRefresh.apply {
            setColorSchemeColors(ThemeUtils.getAttrColor(requireContext(), R.attr.colorAccent))
            setOnRefreshListener(presenter::onRequestRefresh)
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
                if (it.itemId == R.id.menuFindCity) {
                    attachedListener?.onAddCityClick()
                }
                true
            }
            setNavigationOnClickListener {
                attachedListener?.onOpenDrawerClick()
            }
        }
    }

    private fun setupLists() {
        currentForecastHourlyList.adapter = HourlyWeatherAdapter()
        currentForecastDailyList.apply {
            itemAnimator = null
            addItemDecoration(getDivider())
            adapter = DailyWeatherAdapter { index ->
                presenter.onDailyWeatherItemClick(index)
            }
        }
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeUtils.getAttrColor(requireContext(), R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize,
            false,
            resources.getDimensionPixelOffset(R.dimen.medium_padding)
        )
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
        currentForecastViewSwitcher.switchTo(1, true)
    }

    override fun showErrorLayout() {
        currentForecastViewSwitcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        showToast(R.string.network_error)
    }

    override fun showMainLayout() {
        currentForecastViewSwitcher.switchTo(0, true)
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

    override fun setDisplayedDays(count: Int) {
        (currentForecastDailyList.adapter as DailyWeatherAdapter)
            .setDisplayedCount(count)
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
        unitOfTemp: UnitOfTemp
    ) {
        (currentForecastHourlyList.adapter as HourlyWeatherAdapter)
            .submitList(hourlyWeatherList, unitOfTemp)
    }

    override fun setDailyForecast(
        dailyWeather: List<DailyForecast.DailyWeather>,
        unitOfTemp: UnitOfTemp
    ) {
        (currentForecastDailyList.adapter as DailyWeatherAdapter)
            .submitList(dailyWeather, unitOfTemp)
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

    override fun navigateToDailyPagesScreen(location: SavedLocation, dayIndex: Int) {
        attachedListener?.navigateToDailyPagesFragment(location, dayIndex)
    }
}
