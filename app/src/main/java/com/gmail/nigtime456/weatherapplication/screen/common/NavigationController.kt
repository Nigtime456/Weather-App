/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.common

/**
 * Общий листенер для фрагментов
 */
interface NavigationController {
    /**
     * Переключить на следущий экран
     */
    fun navigateTo(screen: Screen)

    fun toPreviousScreen()
}