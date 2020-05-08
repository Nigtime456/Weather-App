/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.settings.UnitFormatter
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import kotlinx.android.synthetic.main.fragment_current_forecast_main.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment :
    BaseFragment<CurrentForecastView, CurrentForecastPresenter, CurrentForecastFragment.ParentListener>(
        R.layout.fragment_current_forecast
    ),
    CurrentForecastView {

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

    override fun provideListenerClass(): Class<ParentListener> = ParentListener::class.java

    override fun getPresenterHolder(): PresenterProvider<CurrentForecastPresenter> {
        return ViewModelProvider(this).get(CurrentForecastViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("sas", "frag = ${hashCode()} presenter = ${presenter.hashCode()}")
        unitFormatter = App.INSTANCE.appContainer.settingsManager.getUnitFormatter()
        initViews()
        presenter.provideForecast(getCurrentCity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unitFormatter = null
    }

    private fun initViews() {
        currentForecastDaySwitcher.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.currentForecastSwitcher5days -> {
                        presenter.onSwitcher5days()
                    }
                    R.id.currentForecastSwitcher10days -> {
                        presenter.onSwitcher10days()
                    }
                    R.id.currentForecastSwitcher16days -> {
                        presenter.onSwitcher16days()
                    }
                }
            }
        }
        currentForecastSwipeRefresh.setOnRefreshListener {
            currentForecastSwipeRefresh.isRefreshing = false
            showToast("TODO!!!")
        }
        setupAppBar()
        initLists()
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

    private fun initLists() {
        currentForecastHourlyList.adapter = HourlyWeatherAdapter(unitFormatter!!)
        currentForecastDailyList.apply {
            itemAnimator = null
            adapter = DailyWeatherAdapter(unitFormatter!!) {
                showToast("TODO = $it")
            }
        }
    }

    private fun getCurrentCity(): CityForForecast = arguments.let {
        it?.getParcelable(EXTRA_CITY) ?: error("required CityForForecast object")
    }


    override fun setCityName(cityName: String) {
        currentForecastToolbar.title = cityName
    }

    override fun showLoadAnimation() {
        currentForecastViewSwitcher.switchTo(1, false)
    }

    override fun showErrorView() {
        currentForecastViewSwitcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        showToast("Error!")
    }

    override fun showMainLayout() {
        currentForecastViewSwitcher.switchTo(0, false)
    }

    override fun setCurrentForecast(currentForecast: CurrentForecast) {
        currentForecastCurrentTemp.text =
            unitFormatter!!.formatTemp(currentForecast.weatherInfo.temp)
        currentForecastCurrentFeelsLikeTemp.text =
            unitFormatter!!.formatFeelsLikeTemp(currentForecast.weatherInfo.feelsLikeTemp)
        currentForecastCurrentDescription.setText(currentForecast.weatherInfo.description)
        currentForecastCurrentIco.setImageResource(currentForecast.weatherInfo.ico)
    }

    override fun setHourlyForecast(hourlyWeatherList: List<HourlyForecast.HourlyWeather>) {
        (currentForecastHourlyList.adapter as HourlyWeatherAdapter).submitList(hourlyWeatherList)
    }

    override fun setDailyForecast(dailyWeather: List<DailyForecast.DailyWeather>) {
        (currentForecastDailyList.adapter as DailyWeatherAdapter).submitList(dailyWeather)
    }


}
