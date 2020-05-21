/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.location

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface SavedLocationsRepository {
    /**
     * Получить все добавленные города
     */
    fun getLocations(): Observable<List<SavedLocation>>

    fun getLocationsOnce(): Single<List<SavedLocation>>

    fun hasLocations(): Single<Boolean>

    fun delete(item: SavedLocation): Completable

    fun replaceAll(items: List<SavedLocation>): Completable
}