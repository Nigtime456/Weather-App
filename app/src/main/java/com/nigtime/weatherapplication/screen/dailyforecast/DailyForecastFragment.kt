/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.dailyforecast

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import kotlinx.android.synthetic.main.fragment_daily_forecast.*

class DailyForecastFragment :
    BaseFragment<DailyForecastView, DailyForecastPresenter, Nothing>(R.layout.fragment_daily_forecast) {

    companion object {
        private const val EXTRA_DAY_INDEX = "weatherapplication.screen.daily_forecast.day_index"
        private const val EXTRA_LOCATION = "weatherapplication.screen.daily_forecast.location"

        fun newInstance(location: SavedLocation, dayIndex: Int): DailyForecastFragment {
            require(dayIndex in 0..15) { "invalid day index = $dayIndex" }
            return DailyForecastFragment().apply {
                arguments = bundleOf(
                    EXTRA_LOCATION to location,
                    EXTRA_DAY_INDEX to dayIndex
                )
            }
        }
    }


    override fun getPresenterProvider(): BasePresenterProvider<DailyForecastPresenter> {
        val dayIndex = arguments?.getInt(EXTRA_DAY_INDEX) ?: 0
        return ViewModelProvider(this).get(
            dayIndex.toString(),
            DailyForecastPresenterProvider::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(EXTRA_DAY_INDEX)
        testTextView.text = index.toString()
    }
}