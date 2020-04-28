/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.WishCity
import io.reactivex.Completable
import io.reactivex.Single

interface WishCitiesRepository {
    /**
     * Получить все добавленные города
     */
    fun getWishCitiesList(): Single<List<WishCity>>

    fun hasWishCities(): Single<Boolean>

    fun delete(item: WishCity): Completable

    fun replaceAll(items: List<WishCity>): Completable
}