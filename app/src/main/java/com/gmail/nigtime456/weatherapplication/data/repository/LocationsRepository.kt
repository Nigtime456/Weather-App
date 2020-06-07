/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */


package com.gmail.nigtime456.weatherapplication.data.repository

import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
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