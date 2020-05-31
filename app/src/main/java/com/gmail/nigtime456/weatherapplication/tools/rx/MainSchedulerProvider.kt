/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.tools.rx

import com.gmail.nigtime456.weatherapplication.tools.AppExecutors
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

