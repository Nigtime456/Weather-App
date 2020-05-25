/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.common.mvp

import com.gmail.nigtime456.weatherapplication.common.di.AppContainer

interface PresenterFactory<T : BasePresenter<*>> {
    fun getPresenter(appContainer: AppContainer): T
}