/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Базовый класс для создания активити с презентером.
 * Подклассы сами присоединяют презентер.
 *
 * @param P - presenter class
 * @param V - view class
 */

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<V : MvpView, P : BasePresenter<V>> : AppCompatActivity() {
    /**
     * Шина для уведомления презентера об событиях ЖЦ
     */
    protected val lifecycleBus: Subject<ExtendLifecycle> = PublishSubject.create()
    /**
     * Презентер
     */
    protected lateinit var presenter: P
    /**
     * Ивент при котором отсоеденить презентер [Lifecycle]
     */
    protected var detachEvent = Lifecycle.Event.ON_DESTROY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = provideMvpPresenter()
    }

    override fun onPause() {
        super.onPause()
        lifecycleBus.onNext(ExtendLifecycle.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleBus.onNext(ExtendLifecycle.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleBus.onNext(ExtendLifecycle.DESTROY)
    }

    /**
     * Предоставить презентер
     */
    protected abstract fun provideMvpPresenter(): P

    /**
     * Предоставить MvpView
     */
    protected abstract fun provideMvpView(): V
}