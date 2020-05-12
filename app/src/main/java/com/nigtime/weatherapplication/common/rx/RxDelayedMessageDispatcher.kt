/*
 * Сreated by Igor Pokrovsky. 2020/4/25
 */

package com.nigtime.weatherapplication.common.rx

import androidx.annotation.MainThread
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit


/**
 * Класс позволяет откладывать выполнения едичного сообщения,
 * если появляется новое сообщение, предыдущее, если оно всё ещё
 * ожидает, будет немедленно выполнено, а новое будет так же
 * поставлено на ожидание.
 *
 * @param durationMillis - время ожидания
 * @param schedulerProvider - [SchedulerProvider]
 */
class RxDelayedMessageDispatcher constructor(
    val durationMillis: Long,
    private val schedulerProvider: SchedulerProvider
) {

    private var pendingMessage: Runnable? = null
    private var dis: Disposable? = null


    /**
     * добавить ожидающее сообщение
     */
    fun delayNextMessage(@MainThread message: Runnable) {
        //если есть предыдущее сообщение - выполняем его
        runCallback()
        //если нет - откладываем
        pendingMessage = message
        delayInvoke()
    }

    /**
     * Немедленно выполнить сообщение
     */
    fun forceRun() {
        dis?.dispose()
        dis = null
        runCallback()
    }

    fun getMessage(): Runnable? {
        return pendingMessage
    }

    fun cancelMessage() {
        dis?.dispose()
        dis = null
        pendingMessage = null
    }

    private fun runCallback() {
        pendingMessage?.run()
        pendingMessage = null
    }

    private fun delayInvoke() {
        //что бы была возможность выполнять принудительно сообщения, будем их отправлять
        //в main thread
        dis = Single.timer(durationMillis, TimeUnit.MILLISECONDS, schedulerProvider.syncDatabase())
            .subscribeOn(schedulerProvider.ui())
            .subscribe(Consumer {
                runCallback()
            })
    }

}