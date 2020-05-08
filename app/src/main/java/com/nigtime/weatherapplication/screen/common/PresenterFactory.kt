/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.common

/**
 * Базовый интерфейс, для класса, который будет создавать презентеры.
 * Фабрика контролирует создание, сохранения при смене кофнигурации, уничтожение презентера
 *
 * @see BasePresenterFactory
 */
interface PresenterFactory<T> {
    fun createPresenter(): T
}