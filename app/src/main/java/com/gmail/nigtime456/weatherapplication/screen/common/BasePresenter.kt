/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.common

import com.gmail.nigtime456.weatherapplication.common.log.CustomLogger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Базовый класс презентера. На View держится слабая ссылка, сам презентер
 * может быть независимым от ЖЦ View, переживать её пересоздание и т.д.
 *
 * @param V - интерфейс MVP View
 * @param tag - тег для логгера.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BasePresenter<V> constructor(tag: String = TAG) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var weakView: WeakReference<V>? = null
    protected val logger: CustomLogger = CustomLogger(tag, true)

    companion object {
        private const val TAG = "base_presenter"
    }

    private var start = 0L
    fun startMeasure() {
        start = System.currentTimeMillis()
    }

    fun endMeasure(): Long = System.currentTimeMillis() - start

    /**
     * Присоеденить презентер
     *
     * @param view - MvpView
     */
    fun attach(view: V) {
        weakView = WeakReference(view)
    }

    /**
     * Отсоеденить презентер, когда экран стал невидимым, View приостановлено.
     */
    fun detach() {
        releaseView()
        logger.d("[${hashCode()}] detach")
    }

    private fun releaseView() {
        weakView?.clear()
        weakView = null
    }

    /**
     * Уничтожить презентер, когда он более не нужен
     */
    fun destroy() {
        performDispose()
        releaseView()
        logger.d("[${hashCode()}] destroy")
    }

    /**
     * true - view присоеденно
     */
    fun isViewAttached(): Boolean {
        return weakView != null && weakView!!.get() != null
    }

    /**
     * true - view отсоеденино
     */
    fun isViewDetached(): Boolean {
        return !isViewAttached()
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
        require(compositeDisposable.add(this)) {
            "unable to add a subscription. CompositeDisposable is disposed ?"
        }
    }

}