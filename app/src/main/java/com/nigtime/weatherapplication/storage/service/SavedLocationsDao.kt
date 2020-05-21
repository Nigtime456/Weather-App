/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.service

import androidx.room.*
import com.nigtime.weatherapplication.storage.table.LocationsTable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface SavedLocationsDao {

    @Query("SELECT *FROM saved_locations ORDER BY list_index ASC")
    fun getAll(): Observable<List<LocationsTable>>

    @Query("SELECT *FROM saved_locations ORDER BY list_index ASC")
    fun getAllOnce(): Single<List<LocationsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(items: List<LocationsTable>)

    @Delete
    fun delete(item: LocationsTable)

    @Insert
    fun insert(item: LocationsTable)

    /**
     * Возвращает текущий максимальный порядковый номер из сохраненных городов
     * @return - максимальный порядковый номер
     */
    @Query("SELECT list_index FROM saved_locations WHERE list_index = (SELECT MAX(list_index) FROM saved_locations)")
    fun getMaxListIndex(): List<Int>

    @Query("SELECT city_id FROM saved_locations")
    fun getAllIds(): List<Long>

    @Query("SELECT * FROM saved_locations LIMIT 1")
    fun getOneRow(): List<LocationsTable>
}