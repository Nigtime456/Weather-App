/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import androidx.annotation.CallSuper
import com.nigtime.weatherapplication.common.log.CustomLogger
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
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
abstract class BasePresenter<V : MvpView> constructor(
    protected val schedulerProvider: SchedulerProvider,
    tag: String = TAG
) {

    private var weakView: WeakReference<V>? = null
    private var compositeDisposable: CompositeDisposable? = null
    protected val logger: CustomLogger = CustomLogger(tag, true)

    companion object {
        private const val TAG = "BasePresenter"
    }

    /**
     * Присоеденить презентер
     * @param view - MvpView
     *
     */
    fun attach(view: V) {
        require(!isViewAttached()) { "view already attached!" }
        weakView = WeakReference(view)
        onAttach()
    }

    /**
     * Вызывается когда презентер присоденен
     */
    @CallSuper
    protected open fun onAttach() {
        compositeDisposable = CompositeDisposable()
    }

    /**
     * Отсоеденить презентер
     */
    fun detach() {
        require(isViewAttached()) { "view already detached!" }
        onDetach()
    }

    /**
     * Вызывается когда презентер отсоеден
     */
    @CallSuper
    protected open fun onDetach() {
        weakView?.clear()
        performDispose()
        weakView = null
        compositeDisposable = null
    }

    /**
     * Очистить все подписки и остановить потоки
     */
    protected fun performDispose() {
        compositeDisposable?.clear()
    }


    /**
     * true - view присоеденно
     */
    protected fun isViewAttached(): Boolean {
        return weakView != null && weakView!!.get() != null
    }

    /**
     * Получить ссылку на присоеденную view.
     * @return присоеденная MvpView или null, если она отсодена
     */
    protected fun getView(): V? = weakView?.get()

    /**
     * Расширение к Disposable для простой отписки
     */
    protected open fun Disposable.disposeOnDetach() {
        compositeDisposable!!.add(this)
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
            .disposeOnDetach()
    }

    /**
     * Расширение к [Observable], подписаться и обрабатывать ошибки и подписку.
     *
     * @param ignoreError - true - ошибка будет проигнорирована, false - проброшена дальше
     * @param onNext - коллбэк
     */
    protected fun <T> Observable<T>.subscribeAndHandleError(
        ignoreError: Boolean = false,
        onNext: (T) -> Unit
    ) {
        subscribe(onNext, { if (!ignoreError) rethrowError(it) })
            .disposeOnDetach()
    }

    /**
     * Расширение к [Flowable], подписаться и обрабатывать ошибки и подписку.
     *
     * @param ignoreError - true - ошибка будет проигнорирована, false - проброшена дальше
     * @param onNext - коллбэк
     */
    protected fun <T> Flowable<T>.subscribeAndHandleError(
        ignoreError: Boolean = false,
        onNext: (T) -> Unit
    ) {
        subscribe(onNext, { if (!ignoreError) rethrowError(it) })
            .disposeOnDetach()
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
            .disposeOnDetach()
    }

}