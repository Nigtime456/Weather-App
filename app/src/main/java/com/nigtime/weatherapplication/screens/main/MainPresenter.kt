/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.main

import com.nigtime.weatherapplication.screens.common.BasePresenter
import com.nigtime.weatherapplication.common.rx.SchedulerProvider

class MainPresenter(schedulerProvider: SchedulerProvider) :
    BasePresenter<MainView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "main"
    }

}