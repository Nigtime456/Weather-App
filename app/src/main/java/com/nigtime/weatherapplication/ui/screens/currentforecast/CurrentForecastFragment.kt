/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.currentforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.database.CityForForecast
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import kotlinx.android.synthetic.main.fragment_current_forecast.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : BaseFragment<CurrentForecastFragment.ParentListener>() {

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

    private val presenter = CurrentForecastPresenter(MainSchedulerProvider.INSTANCE)

    override fun provideListenerClass(): Class<ParentListener> = ParentListener::class.java

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = arguments!!.getParcelable<CityForForecast>(EXTRA_CITY)
        testTitle.text = city!!.cityName
        testId.text = city.cityId.toString()
        configureAppBar()
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


}
