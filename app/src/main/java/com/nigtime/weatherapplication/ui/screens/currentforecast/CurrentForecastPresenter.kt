/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.currentforecast

import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider

class CurrentForecastPresenter(schedulerProvider: SchedulerProvider) :
    BasePresenter<CurrentForecastView>(
        schedulerProvider
    )