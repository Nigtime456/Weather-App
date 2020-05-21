/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.nigtime.weatherapplication.screen.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import leakcanary.AppWatcher

/**
 * Класс управляющий созданием презентера, отвечает за сохранения экземпляров и
 * их уничтожение.
 *
 * Реализован с помощью [ViewModel] позволяя держать презентер пока активити
 * или фрагмент активны.
 */
abstract class BasePresenterProvider<P : BasePresenter<*>> : ViewModel() {
    protected val appContainer = App.INSTANCE.appContainer
    private val presenter = lazy { createPresenter() }

    fun getPresenter(): P = presenter.value

    protected abstract fun createPresenter(): P

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        presenter.value.destroy()
        AppWatcher.objectWatcher.watch(this, "ViewModel should be cleared")
    }
}