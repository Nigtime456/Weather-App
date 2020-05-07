/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import androidx.annotation.CallSuper
import com.nigtime.weatherapplication.common.log.CustomLogger
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import io.reactivex.*
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
        weakView = null
        performDispose()
    }

    /**
     * Выполнить отписку от потоков Rx
     */
    protected fun performDispose() {
        compositeDisposable?.clear()
        compositeDisposable = null
    }

    /**
     * true - view присоеденно
     */
    fun isViewAttached(): Boolean {
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
     */
    protected fun rethrowError(throwable: Throwable, msg: String) {
        logger.e(throwable)
        throw Exception("error on class ${this::class.java.simpleName} = $msg", throwable)
    }

    /**
     * Расширение к [Completable], подписаться и обрабатывать ошибки и подписку.
     */
    protected fun Completable.subscribeAndHandleError(
        ignoreError: Boolean,
        onComplete: () -> Unit
    ) {
        subscribe(onComplete, { if (!ignoreError) rethrowError(it) })
            .disposeOnDetach()
    }

    /**
     * Расширение к [Observable], подписаться и обрабатывать ошибки и подписку.
     */
    protected fun <T> Observable<T>.subscribeAndHandleError(
        ignoreError: Boolean,
        onNext: (T) -> Unit
    ) {
        subscribe(onNext, { if (!ignoreError) rethrowError(it) })
            .disposeOnDetach()
    }

    /**
     * Расширение к [Flowable], подписаться и обрабатывать ошибки и подписку.
     */
    protected fun <T> Flowable<T>.subscribeAndHandleError(
        ignoreError: Boolean,
        onNext: (T) -> Unit
    ) {
        subscribe(onNext, { if (!ignoreError) rethrowError(it) })
            .disposeOnDetach()
    }


    /**
     * Расширение к [Single], подписаться и обрабатывать ошибки и подписку.
     */
    protected fun <T> Single<T>.subscribeAndHandleError(
        ignoreError: Boolean,
        onResult: (T) -> Unit
    ) {
        subscribe(onResult, { if (!ignoreError) rethrowError(it) })
            .disposeOnDetach()
    }


    //TODO
    fun <T> runAsyncResultOnUi(scheduler: Scheduler = schedulerProvider.io()): (Single<T>) -> Single<T> =
        { single ->
            single.subscribeOn(scheduler)
            single.observeOn(schedulerProvider.ui())
            single
        }

}