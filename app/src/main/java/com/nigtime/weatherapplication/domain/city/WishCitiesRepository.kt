/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.city

import io.reactivex.Completable
import io.reactivex.Single

interface WishCitiesRepository {
    /**
     * Получить все добавленные города
     */
    fun getListCities(): Single<List<WishCity>>

    fun hasCities(): Single<Boolean>

    fun delete(item: WishCity): Completable

    fun replaceAll(items: List<WishCity>): Completable
}