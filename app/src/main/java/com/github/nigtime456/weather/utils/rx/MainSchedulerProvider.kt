/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.utils.rx

import com.github.nigtime456.weather.utils.AppExecutors
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.from(AppExecutors.IOExecutor)
    override fun single(): Scheduler = Schedulers.from(AppExecutors.singleExecutor)
    override fun database(): Scheduler = Schedulers.from(AppExecutors.dataBaseExecutor)
}

