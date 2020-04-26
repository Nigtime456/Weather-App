/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.utility.rx

import io.reactivex.Scheduler

/**
 * Интерфейс позволяющий сделать инжект Rx дистпетчеров,
 * позволяя подменять дистетчеры не прибегая к RxJavaPlugins.setIoSchedulerHandler() or etc
 */
interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
    fun single(): Scheduler
}