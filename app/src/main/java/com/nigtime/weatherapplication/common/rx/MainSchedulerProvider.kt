/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.common.rx

import com.nigtime.weatherapplication.common.util.ExecutorsHolder
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.from(ExecutorsHolder.IOExecutor)
    override fun single(): Scheduler = Schedulers.from(ExecutorsHolder.singleExecutor)
    override fun database(): Scheduler = Schedulers.from(ExecutorsHolder.dataBaseExecutor)
}

