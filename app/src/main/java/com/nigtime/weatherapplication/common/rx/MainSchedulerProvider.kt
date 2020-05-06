/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.common.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors


class MainSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.from(Executors.newFixedThreadPool(3))
    override fun syncDatabase(): Scheduler = Schedulers.single()

}

