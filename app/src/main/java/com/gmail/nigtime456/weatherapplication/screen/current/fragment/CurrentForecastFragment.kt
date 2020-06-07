/*
 * Сreated by Igor Pokrovsky. 2020/5/26 
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.data.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.base.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.di.CurrentForecastModule
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.di.DaggerCurrentForecastComponent
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.list.*
import com.gmail.nigtime456.weatherapplication.tools.ui.AnimationHelper
import com.gmail.nigtime456.weatherapplication.tools.ui.getColorFromAttr
import com.gmail.nigtime456.weatherapplication.tools.ui.getDimension
import com.gmail.nigtime456.weatherapplication.tools.ui.showSnackBar
import com.gmail.nigtime456.weatherapplication.ui.list.ColorDividerDecoration
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import javax.inject.Inject

/**
 * Главный экран с погодой
 */
class CurrentForecastFragment :
    BaseFragment<CurrentForecastFragment.ParentActivity>(R.layout.fragment_current_forecast),
    CurrentForecastFragmentContract.View {

    interface ParentActivity {
        fun requestSearchScreen()
        fun requestOpenDrawer()
        fun requestDailyForecastScreen(location: SavedLocation, dayIndex: Int)
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

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: CurrentForecastFragmentContract.Presenter

    @Inject
    lateinit var settingsProvider: SettingsProvider

    private lateinit var screenAdapter: FlexibleAdapter<IFlexible<*>>

    //</editor-fold>

    //<editor-fold desc = "lifecycle">

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

    //</editor-fold>

    //<editor-fold desc = "initialization">

    override fun getListenerClass(): Class<ParentActivity> = ParentActivity::class.java

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

    private fun initViews() {
        initAppBar()
        initSwipeToRefresh()
        initList()
    }

    private fun initList() {
        NestedScrollViewSynchronizer(lifecycle, currentForecastFragmentNestedScrollView)
        screenAdapter = FlexibleAdapter(null)
        currentForecastFragmentRecycler.apply {
            adapter = screenAdapter
            addItemDecoration(getDivider())
            setHasFixedSize(true)
        }
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = requireContext().getColorFromAttr(R.attr.colorControlHighlight)
        val dividerSize = requireContext().getDimension(R.dimen.divider_size)
        val padding = requireContext().getDimension(R.dimen.spacing_medium)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize,
            paddingHorizontal = padding
        )
    }

    private fun initSwipeToRefresh() {

    }

    private fun initAppBar() {
        currentForecastFragmentToolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menuFindCity -> {
                        parentListener?.requestSearchScreen()
                    }
                }
                true
            }
            setNavigationOnClickListener {
                parentListener?.requestOpenDrawer()
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun setLocationName(name: String) {
        currentForecastFragmentLocationName.text = name
    }

    override fun setTimezone(timeZone: String) {
        currentForecastFragmentClock.timeZone = timeZone
        AnimationHelper.fadeIn(currentForecastFragmentClock, R.integer.short_anim)
    }

    override fun showLoadLayout() {
        currentForecastFragmentViewSwitcher.switchTo(1, true)
    }

    override fun showErrorLayout() {
        currentForecastFragmentViewSwitcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        currentForecastFragmentSwipeRefresh.showSnackBar(R.string.network_error)
    }

    override fun showMainLayout() {
        currentForecastFragmentViewSwitcher.switchTo(0, true)
    }

    override fun disableRefreshLayout() {
        //TODO сделать
    }

    override fun enableRefreshLayout() {
        //TODO сделать
    }

    override fun stopRefreshing() {
        //TODO сделать
    }

    override fun showCurrentWeather(
        position: Int,
        weather: CurrentForecast.Weather,
        unitOfTemp: UnitOfTemp
    ) {
        screenAdapter.updateIfContains(position, CurrentWeatherModel(weather, unitOfTemp))
    }

    override fun showHourlyWeather(
        position: Int,
        weatherList: List<HourlyForecast.Weather>,
        unitOfTemp: UnitOfTemp
    ) {
        screenAdapter.updateIfContains(position, HourlyCardModel(weatherList, unitOfTemp))
    }

    override fun showDailyWeather(
        position: Int,
        weatherList: List<DailyForecast.Weather>,
        unitOfTemp: UnitOfTemp
    ) {
        screenAdapter.updateIfContains(position, DailyCardModel(weatherList, unitOfTemp))
    }

    override fun showDetailedWeather(
        position: Int,
        detailedWeather: CurrentForecast.DetailedWeather,
        unitOfSpeed: UnitOfSpeed,
        unitOfPressure: UnitOfPressure,
        unitOfLength: UnitOfLength
    ) {
        screenAdapter.updateIfContains(
            position,
            DetailedWeatherModel(detailedWeather, unitOfSpeed, unitOfPressure, unitOfLength)
        )
    }

    override fun showEnvironment(position: Int, environment: CurrentForecast.Environment) {
        screenAdapter.updateIfContains(position, EnvironmentModel(environment))
    }

    private fun FlexibleAdapter<IFlexible<*>>.updateIfContains(position: Int, item: IFlexible<*>) {
        if (getItemViewType(position) != item.itemViewType) {
            addItem(position, item)
        } else {
            val old = getItem(position)
            //update only if different
            if (old != item)
                updateItem(position, item, null)
        }
    }

    //</editor-fold>

}
