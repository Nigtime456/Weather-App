/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable

@Dao
interface ReferenceCitiesDao {
    /**
     * поиск по имени.
     */
    @Query("SELECT * FROM reference_cities WHERE city_name LIKE :name ORDER BY city_name ASC LIMIT :startPosition, :count ")
    fun queryByName(name: String, startPosition: Int, count: Int): List<ReferenceCitiesTable>

    //TODO удалить в будущем
    @Insert
    fun insert(item: ReferenceCitiesTable)

    //TODO удалить в будущем
    @Query("SELECT * FROM reference_cities LIMIT 1")
    fun getOneRow(): List<ReferenceCitiesTable>
}

