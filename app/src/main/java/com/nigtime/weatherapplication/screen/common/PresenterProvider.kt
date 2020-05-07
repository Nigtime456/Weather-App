/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.common

interface PresenterProvider <T> {
    fun providePresenter() : T
}