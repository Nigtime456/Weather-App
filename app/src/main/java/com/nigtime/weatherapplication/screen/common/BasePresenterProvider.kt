/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.nigtime.weatherapplication.screen.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import leakcanary.AppWatcher

/**
 * Реализация [PresenterProvider], сохраняет презентер в [ViewModel],
 * позволяя держать при смене конфигурации, но удалять когда фрагмент окончательно удаляется.
 *
 */
abstract class BasePresenterProvider<T : BasePresenter<*>> : ViewModel(), PresenterProvider<T> {
    private val presenter = lazy { createPresenter() }
    protected val appContainer = App.INSTANCE.appContainer

    final override fun getPresenter(): T = presenter.value

    protected abstract fun createPresenter(): T

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        presenter.value.destroy()
        AppWatcher.objectWatcher.watch(this, "ViewModel should be cleared")
    }
}