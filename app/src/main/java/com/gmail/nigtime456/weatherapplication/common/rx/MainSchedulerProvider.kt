/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.common.rx

import com.gmail.nigtime456.weatherapplication.common.util.ExecutorsFactory
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.from(ExecutorsFactory.IOExecutor)
    override fun single(): Scheduler = Schedulers.from(ExecutorsFactory.singleExecutor)
    override fun database(): Scheduler = Schedulers.from(ExecutorsFactory.dataBaseExecutor)
}

