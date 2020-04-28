/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import androidx.annotation.CallSuper
import com.nigtime.weatherapplication.utility.log.CustomLogger
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider
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

    private lateinit var weakView: WeakReference<V>
    private lateinit var compositeDisposable: CompositeDisposable
    protected val logger: CustomLogger = CustomLogger(tag, true)

    companion object {
        private const val TAG = "BasePresenter"
    }

    /**
     * Присоеденить презентер
     * @param view - MvpView
     * @param lifecycleBus - поток для уведомления об ЖЦ фрагмента
     * @param detachOn - ивент [ExtendLifecycle] при котором отписаться
     */
    fun attach(
        view: V,
        lifecycleBus: Observable<ExtendLifecycle>,
        detachOn: ExtendLifecycle = ExtendLifecycle.DESTROY_VIEW
    ) {
        logger.d("presenter is attach = ${hashCode()}")

        weakView = WeakReference(view)


        compositeDisposable = CompositeDisposable()
        lifecycleBus
            .subscribe { lifecycleEvent ->
                logger.d("event = $lifecycleEvent")
                if (lifecycleEvent == detachOn) {
                    onDetach()

                }
            }
            .disposeOnDetach()
    }

    /**
     * вызывается когда презентер отсоеден
     */
    @CallSuper
    protected open fun onDetach() {
        logger.d("presenter is detach = ${hashCode()}")
        weakView.clear()
        performDispose()
    }

    /**
     * Выполнить отписку от потоков Rx
     */
    protected fun performDispose() {
        compositeDisposable.clear()
    }

    /**
     * true - view присоеденно
     */
    fun isViewAttached(): Boolean {
        return weakView.isEnqueued
    }

    /**
     * Получить ссылку на присоеденную view.
     * @return присоеденная MvpView или null, если она отсодена
     */
    protected fun getView(): V? = weakView.get()

    /**
     * Расширение к Disposable для простой отписки
     */
    protected open fun Disposable.disposeOnDetach() {
        compositeDisposable.add(this)
    }


    /**
     * Общий метод для ошибок в потоках Rx
     * @param throwable - исключения, которое будет проигнорировано и
     * залогировано
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

}