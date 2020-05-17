/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.service

import androidx.room.*
import com.nigtime.weatherapplication.storage.table.LocationTable
import io.reactivex.Flowable


@Dao
interface SavedLocationsDao {

    @Query("SELECT *FROM saved_locations ORDER BY list_index ASC")
    fun getAll(): List<LocationTable>

    @Query("SELECT city_id FROM saved_locations")
    fun getAllIds(): List<Long>

    @Query("SELECT city_id FROM saved_locations")
    fun getAllIdsAsFlow(): Flowable<List<Long>>

    @Query("SELECT city_name FROM reference_cities WHERE city_id == :id")
    fun getCityName(id: Long): String

    @Insert
    fun insert(item: LocationTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplaceAll(items: List<LocationTable>)

    @Delete
    fun delete(item: LocationTable)

    /**
     * Возвращает текущий максимальный порядковый номер из сохраненных городов
     * @return - максимальный порядковый номер
     */
    @Query("SELECT list_index FROM saved_locations WHERE list_index = (SELECT MAX(list_index) FROM saved_locations)")
    fun getMaxListIndex(): List<Int>

    @Query("SELECT * FROM saved_locations LIMIT 1")
    fun getOneRow(): List<LocationTable>
}