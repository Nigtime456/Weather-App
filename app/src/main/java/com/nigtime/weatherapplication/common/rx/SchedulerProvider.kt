/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.common.rx

import io.reactivex.Scheduler

/**
 * Интерфейс позволяющий сделать инжект Rx дистпетчеров,
 * позволяя подменять дистетчеры не прибегая к RxJavaPlugins.setIoSchedulerHandler() or etc
 */
interface SchedulerProvider {
    /**
     * UI поток
     */
    fun ui(): Scheduler

    /**
     * Вспомогательные потоки, для ресурсов, которым не нужна сихнронизация
     */
    fun io(): Scheduler

    /**
     * Одиночный поток для синхронизации операций с БД
     */
    fun syncDatabase(): Scheduler
}