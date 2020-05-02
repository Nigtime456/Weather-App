/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nigtime.weatherapplication.db.table.ReferenceCityTable
import io.reactivex.Single

@Dao
interface ReferenceCityDao {
    /**
     * поиск по имени.
     */
    @Query("SELECT * FROM reference_city WHERE city_name LIKE :name ORDER BY city_name ASC LIMIT :startPosition, :count ")
    fun queryByName(name: String, startPosition: Int, count: Int): List<ReferenceCityTable>

    @Query("SELECT * FROM reference_city WHERE city_id = :cityId")
    fun getById(cityId: Long): ReferenceCityTable

    //TODO удалить в будущем
    @Insert
    fun insert(city: ReferenceCityTable)

    //TODO удалить в будущем
    @Query("SELECT * FROM reference_city LIMIT 1")
    fun getOneRow(): Single<List<ReferenceCityTable>>
}

