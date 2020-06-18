/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */


package com.github.nigtime456.weather.data.repository

import com.github.nigtime456.weather.data.location.SavedLocation
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocationsRepository {
    /**
     * Получить все добавленные города
     */
    fun getLocations(): Observable<List<SavedLocation>>

    fun hasLocations(): Single<Boolean>

    fun delete(item: SavedLocation): Completable

    fun update(items: List<SavedLocation>): Completable
}