/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.utility.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors


class MainSchedulerProvider private constructor() : SchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.from(Executors.newFixedThreadPool(3))
    override fun single(): Scheduler = Schedulers.single()

    companion object {
        val INSTANCE = MainSchedulerProvider()
    }
}

