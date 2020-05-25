/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.common.mvp

import com.gmail.nigtime456.weatherapplication.common.di.AppContainer


class PresenterProvider(private val appContainer: AppContainer) {
    private val map = mutableMapOf<String, BasePresenter<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : BasePresenter<*>> getPresenter(key: String, factory: PresenterFactory<T>): T {
        return map.getOrPut(key, { factory.getPresenter(appContainer) }) as T
    }

    fun removePresenter(key: String) {
        map.remove(key)?.destroy()
    }
}