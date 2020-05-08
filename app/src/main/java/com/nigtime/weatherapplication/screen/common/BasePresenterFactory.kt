/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.nigtime.weatherapplication.screen.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import leakcanary.AppWatcher

/**
 * Реализация [PresenterFactory], сохраняет презентер в [ViewModel],
 * позволя держать при смене конфигурации, но удалять когда фрагмент окончательно удаляется.
 *
 *
 */
abstract class BasePresenterFactory<T> : ViewModel(), PresenterFactory<T> {
    protected val appContainer = App.INSTANCE.appContainer

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        AppWatcher.objectWatcher.watch(this, "ViewModel should be cleared")
    }
}