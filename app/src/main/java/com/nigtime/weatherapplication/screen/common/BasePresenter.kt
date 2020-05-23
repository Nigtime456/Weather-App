/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import com.nigtime.weatherapplication.common.log.CustomLogger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Базовый класс презентера. На View держится слабая ссылка, сам презентер
 * может быть независимым от ЖЦ View, переживать её пересоздание и т.д.
 *
 * Контракт:
 * 1)[attach] - вызывается когда View присоеденно, отображается на экране,
 * и готово получать и отображать данные.
 * [onAttach] - может начинаться загрузка данных, настройка экрана.
 *
 * 2)[detach] - вызывается когда View отсоеденияется.
 * [onDetach] - могут быть заданы какие то параметры, скрыты диалоги, сообщения.
 *
 * 3)[destroy] - вызывается когда презентер более не нужен (фрагмент уничтожается полностью,
 * удаляется из стэк переходов)
 * [onDestroy] - освобождаются ресурсы, удаляются подписки, соедения с сестью, базой данных и т.д.
 *
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
        onAttach()
        logger.d("[${hashCode()}] attach")
    }

    /**
     * Отсоеденить презентер, когда экран стал невидимым, View приостановлено.
     */
    fun detach() {
        onDetach()
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
        onDestroy()
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
     * Вызывается когда презентер присоденен
     */
    protected open fun onAttach() {

    }

    /**
     * Вызывается когда вью будет отсоедено
     */
    protected open fun onDetach() {

    }

    /**
     * Вызывается когда презентер будет уничтожен
     */
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
        require(compositeDisposable.add(this)) {
            "unable to add a subscription. CompositeDisposable is disposed ?"
        }
    }

}