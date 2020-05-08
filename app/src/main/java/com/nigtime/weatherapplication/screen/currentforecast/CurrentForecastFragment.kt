/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.settings.UnitFormatter
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.PresenterFactory
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
        unitFormatter = App.INSTANCE.appContainer.settingsManager.getUnitFormatter()
        initViews()
        presenter.provideForecast(currentCity.value)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        unitFormatter = null
    }

    private fun initViews() {
        setupDaysSwitcher()
        setupAppBar()
        setupLists()
    }

    private fun setupDaysSwitcher() {
        currentForecastDaysSwitcher.addOnButtonCheckedListener { _, checkedId, isChecked ->
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

    override fun setCityName(cityName: String) {
        currentForecastToolbar.title = cityName
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
