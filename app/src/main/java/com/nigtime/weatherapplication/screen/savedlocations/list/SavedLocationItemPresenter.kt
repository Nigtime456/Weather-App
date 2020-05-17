/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.screen.common.BasePresenter

class SavedLocationItemPresenter(schedulerProvider: SchedulerProvider) :
    BasePresenter<SavedLocationItemView>(
        schedulerProvider
    )