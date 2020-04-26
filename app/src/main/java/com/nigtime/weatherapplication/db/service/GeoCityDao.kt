/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nigtime.weatherapplication.db.tables.GeoCityTable
import io.reactivex.Single

@Dao
interface GeoCityDao {
    /**
     * поиск по имени.
     */
    @Query("SELECT * FROM geonames WHERE city_name LIKE :name ORDER BY city_name ASC LIMIT :startPosition, :count ")
    fun queryByName(name: String, startPosition: Int, count: Int): List<GeoCityTable>

    @Query("SELECT * FROM geonames WHERE city_id = :cityId")
    fun getById(cityId: Long): GeoCityTable

    @Query("SELECT city_name FROM geonames WHERE city_id = :cityId")
    fun getNameById(cityId: Long): String

    //TODO удалить в будущем
    @Insert
    fun insert(city: GeoCityTable)

    //TODO удалить в будущем
    @Query("SELECT * FROM geonames LIMIT 1")
    fun getOneRow(): Single<List<GeoCityTable>>
}

