/*
 * Сreated by Igor Pokrovsky. 2020/5/26 
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.transition.TransitionManager
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.domain.forecast.*
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.domain.util.UnitFormatHelper
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.ColorDividerDecoration
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BaseFragment
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di.CurrentForecastModule
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di.DaggerCurrentForecastComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.list.DailyWeatherAdapter
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.list.HourlyWeatherAdapter
import com.gmail.nigtime456.weatherapplication.ui.util.ThemeUtils
import com.gmail.nigtime456.weatherapplication.ui.util.showSnackBar
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main.*
import javax.inject.Inject


/**
 * Главный экран с погодой
 */
class CurrentForecastFragment :
    BaseFragment<CurrentForecastFragment.Parent>(R.layout.fragment_current_forecast),
    CurrentForecastContract.View {

    interface Parent {
        fun clickAddCity()
        fun openDrawer()
        fun requestDailyForecast(location: SavedLocation, dayIndex: Int)
    }

    companion object {
        private const val EXTRA_LOCATION = "weatherapplication.screen.current.fragment.location"

        fun newInstance(location: SavedLocation): CurrentForecastFragment {
            return CurrentForecastFragment()
                .apply {
                    arguments = bundleOf(EXTRA_LOCATION to location)
                }
        }
    }

    @Inject
    lateinit var unitFormatHelper: UnitFormatHelper

    @Inject
    lateinit var presenter: CurrentForecastContract.Presenter

    @Inject
    lateinit var rxAsyncDiffer: RxAsyncDiffer

    private val nullScrollListener: NestedScrollView.OnScrollChangeListener? = null


    override fun getListenerClass(): Class<Parent> = Parent::class.java


    override fun initDi(appComponent: AppComponent) {
        val currentLocation = getLocation()
        DaggerCurrentForecastComponent.builder()
            .appComponent(appComponent)
            .currentForecastModule(CurrentForecastModule(this, currentLocation))
            .build()
            .inject(this)
    }

    private fun getLocation(): SavedLocation {
        return arguments?.getParcelable(EXTRA_LOCATION) ?: error("require location")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.provideForecast()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.provideForecast()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentForecastScrollView.setOnScrollChangeListener(nullScrollListener)
    }

    private fun initViews() {
        initDaysSwitcher()
        initAppBar()
        initLists()
        initScrollView()
        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        currentForecastSwipeRefresh.apply {
            setColorSchemeColors(ThemeUtils.getAttrColor(requireContext(), R.attr.colorAccent))
            setOnRefreshListener(presenter::refreshData)
        }
    }

    private fun initDaysSwitcher() {
        currentForecastDaysSwitcher.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                presenter.changeDisplayedDays(checkedId)
            }
        }
    }

    private fun initAppBar() {
        currentForecastToolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menuFindCity -> {
                        parentListener?.clickAddCity()
                    }
                }
                true
            }
            setNavigationOnClickListener {
                parentListener?.openDrawer()
            }
        }
    }

    private fun initLists() {
        currentForecastHourlyList.adapter = HourlyWeatherAdapter(rxAsyncDiffer)
        currentForecastDailyList.apply {
            itemAnimator = null
            addItemDecoration(getDivider())
            adapter = DailyWeatherAdapter(presenter::clickDailyWeatherItem, rxAsyncDiffer)
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

    private fun initScrollView() {
        currentForecastScrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            presenter.changeScroll(scrollY)
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
        currentForecastSwipeRefresh.showSnackBar(R.string.network_error)
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
        currentForecastCurrentTemp.text = unitFormatHelper.formatTemp(unitOfTemp, temp)
    }

    override fun setCurrentFeelsLikeTemp(temp: Double, unitOfTemp: UnitOfTemp) {
        currentForecastCurrentFeelsLikeTemp.text =
            unitFormatHelper.formatFeelsLikeTemp(unitOfTemp, temp)
    }

    override fun setCurrentIco(ico: Int) {
        currentForecastCurrentIco.setImageResource(ico)
    }

    override fun setCurrentWeatherDescription(description: Int) {
        currentForecastCurrentDescription.setText(description)
    }

    override fun setHourlyForecast(
        hourlyWeatherList: List<HourlyWeather>,
        unitOfTemp: UnitOfTemp
    ) {
        (currentForecastHourlyList.adapter as HourlyWeatherAdapter)
            .submitList(hourlyWeatherList, unitOfTemp)
    }

    override fun setDailyForecast(
        dailyWeather: List<DailyWeather>,
        unitOfTemp: UnitOfTemp
    ) {
        (currentForecastDailyList.adapter as DailyWeatherAdapter)
            .submitList(dailyWeather, unitOfTemp)
    }

    override fun setWind(wind: Wind, unitOfSpeed: UnitOfSpeed) {
        currentForecastWindIndicator.rotation = wind.degrees.toFloat()
        currentForecastWindSpeed.text = unitFormatHelper.formatSpeed(unitOfSpeed, wind.speed)
        currentForecastWindDirChars.setText(wind.cardinalDirection.getAbbreviatedName())
    }

    override fun setHumidity(humidity: Int) {
        currentForecastHumidity.text = unitFormatHelper.formatHumidity(humidity)
    }

    override fun setPressure(pressure: Double, unitOfPressure: UnitOfPressure) {
        currentForecastPressure.text = unitFormatHelper.formatPressure(unitOfPressure, pressure)
    }

    override fun setVisibility(visibility: Double, unitOfLength: UnitOfLength) {
        currentForecastVisibility.text =
            unitFormatHelper.formatVisibility(unitOfLength, visibility)
    }

    override fun setAirQuality(airQuality: AirQuality) {
        currentForecastAirQuality.text = unitFormatHelper.formatAirQuality(airQuality)
    }

    override fun setUvIndex(uvIndex: UvIndex) {
        currentForecastUvIndex.text = unitFormatHelper.formatUvIndex(uvIndex)
    }

    override fun setClouds(clouds: Int) {
        currentForecastClouds.text = unitFormatHelper.formatCloudsCoverage(clouds)
    }

    override fun setSunInfo(sunInfo: SunInfo) {
        //TODO сделать
    }

    override fun showDailyForecastScreen(location: SavedLocation, dayIndex: Int) {
        parentListener?.requestDailyForecast(location, dayIndex)
    }

}
