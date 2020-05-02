/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.currentforecast

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import com.nigtime.weatherapplication.ui.helpers.UnitFormatter
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.utility.di.ForecastManagerFactory
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.utility.testing.FakeSettingManager
import kotlinx.android.synthetic.main.fragment_current_forecast.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : BaseFragment<CurrentForecastFragment.ParentListener>(),
    CurrentForecastView {

    interface ParentListener {
        fun onClickAddCity()
        fun onClickOpenDrawer()
    }

    companion object {
        //TODO constant
        private const val EXTRA_CITY = "com.nigtime.weatherapp.current_forecast.forecast_city"

        fun newInstance(cityForForecast: CityForForecast): CurrentForecastFragment {
            return CurrentForecastFragment().apply {
                arguments = bundleOf(EXTRA_CITY to cityForForecast)
            }
        }
    }

    private lateinit var presenter: CurrentForecastPresenter

    override fun provideListenerClass(): Class<ParentListener> = ParentListener::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = CurrentForecastPresenter(
            MainSchedulerProvider.INSTANCE,
            ForecastManagerFactory.getForecastManager(),
            UnitFormatter(context, FakeSettingManager())
        )
        presenter.attach(this, lifecycleBus)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAppBar()
        initLists()
        presenter.setUp(getCurrentCity())
    }

    private fun initLists() {
        currentForecastHourlyList.adapter = HourlyWeatherAdapter()
    }

    private fun getCurrentCity(): CityForForecast {
        return arguments.let {
            it?.getParcelable(EXTRA_CITY) ?: error("required CityForForecast object")
        }
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

    override fun setCityName(cityName: String) {
        currentForecastToolbar.title = cityName
    }

    override fun showLoadAnimation() {
        currentForecastViewSwitcher.switchTo(1, true)
    }

    override fun showErrorView() {
        currentForecastViewSwitcher.switchTo(2, true)
    }

    override fun showErrorMessage() {
        Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
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
