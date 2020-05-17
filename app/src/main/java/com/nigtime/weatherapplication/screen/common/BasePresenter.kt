/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import com.nigtime.weatherapplication.common.log.CustomLogger
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.common.utility.RetainedContainer
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Базовый класс презентера.
 *
 * @param schedulerProvider - поставщик потоков для Rx. [SchedulerProvider]
 * @param tag - тег для логгера.
 *
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BasePresenter<V> constructor(
    protected val schedulerProvider: SchedulerProvider,
    tag: String = TAG
) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var weakView: WeakReference<V>? = null
    protected val logger: CustomLogger = CustomLogger(tag, true)
    protected val retainedContainer = RetainedContainer()

    companion object {
        private const val TAG = "base_presenter"
    }

    /**
     * Присоеденить презентер
     * @param view - MvpView
     *
     */
    fun attach(view: V) {
        require(isViewDetached()) { "view already attached!" }
        weakView = WeakReference(view)
        onAttach()

        logger.d("attached")
    }

    /**
     * Отсоеденить презентер
     */
    fun detach() {
        require(isViewAttached()) { "view already detached!" }
        onDetach()
        weakView?.clear()
        weakView = null

        logger.d("detached")
    }

    fun destroy() {
        require(weakView == null) { "this method should be called after detach!" }
        performDispose()
        onDestroy()

        logger.d("destroyed")
    }

    /**
     * true - view присоеденно
     */
    fun isViewAttached(): Boolean {
        return weakView != null && weakView!!.get() != null
    }

    fun isViewDetached(): Boolean {
        return !isViewAttached()
    }

    /**
     * Вызывается когда презентер присоденен
     */
    protected open fun onAttach() {

    }

    /**
     * Вызывается когда вью будет отсоедено
     */
    protected open fun onDetach() {

    }

    protected open fun onDestroy() {

    }

    /**
     * Очистить все подписки и остановить потоки
     */
    protected fun performDispose() {
        compositeDisposable.clear()
    }

    /**
     * Получить ссылку на присоеденную view.
     * @return присоеденная MvpView или null, если она отсодена
     */
    protected fun getView(): V? = weakView?.get()

    /**
     * Расширение к Disposable для простой отписки
     */
    protected open fun Disposable.disposeOnDestroy() {
        compositeDisposable.add(this)
    }

    /**
     * Общий метод для ошибок в потоках Rx
     * @param throwable - исключения, в базовой реализации оно будет
     * проигнорировано.
     */
    protected open fun onStreamError(throwable: Throwable) {
        logger.e(throwable)
    }

    /**
     * Пробросить исключение полученное в потоке Rx
     * @param throwable - пробрасываемое исключения
     */
    protected fun rethrowError(throwable: Throwable) {
        rethrowError(throwable, "")
    }

    /**
     * Пробросить исключение полученное в потоке Rx
     * @param throwable - пробрасываемое исключения
     * @param msg - сообщение
     */
    protected fun rethrowError(throwable: Throwable, msg: String) {
        logger.e(throwable)
        throw Exception("error on class ${this::class.java.simpleName} = $msg", throwable)
    }

    /**
     * Расширение к [Completable], подписаться и обрабатывать ошибки и подписку.
     *
     * @param ignoreError - true - ошибка будет проигнорирована, false - проброшена дальше
     * @param onComplete - коллбэк
     */
    protected fun Completable.subscribeAndHandleError(
        ignoreError: Boolean = false,
        onComplete: () -> Unit
    ) {
        subscribe(onComplete, { if (!ignoreError) rethrowError(it) })
            .disposeOnDestroy()
    }

    /**
     * Расширение к [Observable], подписаться и обрабатывать ошибки и подписку.
     *
     * @param ignoreError - true - ошибка будет проигнорирована, false - проброшена дальше
     * @param onNext - коллбэк
     */
    protected fun <T> Observable<T>.subscribeAndHandleError(
        ignoreError: Boolean = false,
        onComplete: () -> Unit = {},
        onNext: (T) -> Unit
    ) {
        subscribe(onNext, { if (!ignoreError) rethrowError(it) }, onComplete)
            .disposeOnDestroy()
    }

    /**
     * Расширение к [Flowable], подписаться и обрабатывать ошибки и подписку.
     *
     * @param ignoreError - true - ошибка будет проигнорирована, false - проброшена дальше
     * @param onNext - коллбэк
     */
    protected fun <T> Flowable<T>.subscribeAndHandleError(
        ignoreError: Boolean = false,
        onComplete: () -> Unit = {},
        onNext: (T) -> Unit
    ) {
        subscribe(onNext, { if (!ignoreError) rethrowError(it) }, onComplete)
            .disposeOnDestroy()
    }


    /**
     * Расширение к [Single], подписаться и обрабатывать ошибки и подписку.
     *
     * @param ignoreError - true - ошибка будет проигнорирована, false - проброшена дальше
     * @param onResult - коллбэк
     */
    protected fun <T> Single<T>.subscribeAndHandleError(
        ignoreError: Boolean = false,
        onResult: (T) -> Unit
    ) {
        subscribe(onResult, { if (!ignoreError) rethrowError(it) })
            .disposeOnDestroy()
    }

}