/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
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


    override fun provideListenerClass(): Class<ParentListener> = ParentListener::class.java

    override fun getPresenterHolder(): PresenterProvider<CurrentForecastPresenter> {
        return ViewModelProvider(this).get(CurrentForecastViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.provideForecast(getCurrentCity())
    }

    private fun initViews() {
        configureAppBar()
        initLists()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sas","create vm = ${getPresenterHolder().hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("sas","destroyView vm = ${getPresenterHolder().hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("sas","destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("sas","dettach")
    }


    private fun configureAppBar() {
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
        currentForecastHourlyList.adapter = HourlyWeatherAdapter()
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

    override fun setCurrentTemp(temp: String) {
        currentForecastCurrentTemp.text = temp
    }

    override fun setCurrentFeelsLikeTemp(temp: String) {
        currentForecastCurrentFeelsLikeTemp.text = temp
    }

    override fun setCurrentDescription(description: String) {
        currentForecastCurrentDescription.text = description
    }

    override fun setCurrentTempIco(ico: Int) {
        currentForecastCurrentIco.setImageResource(ico)
    }

    override fun showHourlyForecast(hourlyWeatherList: List<HourlyForecast.HourlyWeather>) {
        (currentForecastHourlyList.adapter as HourlyWeatherAdapter).submitList(hourlyWeatherList)
    }

}
