/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.main

import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider

class MainPresenter(schedulerProvider: SchedulerProvider) :
    BasePresenter<MainView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "main"
    }

}