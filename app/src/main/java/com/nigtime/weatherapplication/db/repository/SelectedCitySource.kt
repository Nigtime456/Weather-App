/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.data.SelectedCityData
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Абстракция над таблицой, для простого получения данных.
 *
 */
interface SelectedCitySource {
    /**
     * Получить все сохраненные города однакратно
     */
    fun getAllAsSingle(): Single<List<SelectedCityData>>

    /**
     * Получить все ID's сохраненных городов
     */
    fun getAllIds(): Single<Set<Long>>

    fun hasCities(): Single<Boolean>

    fun insert(item: SearchCityData): Completable

    fun delete(item: SelectedCityData): Completable

    fun replaceAll(items: List<SelectedCityData>): Completable
}