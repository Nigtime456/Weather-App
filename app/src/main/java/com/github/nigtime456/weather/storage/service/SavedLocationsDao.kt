/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.storage.service

import androidx.room.*
import com.github.nigtime456.weather.storage.table.SavedLocationTable
import io.reactivex.Observable

@Dao
interface SavedLocationsDao {

    @Query("SELECT *FROM saved_locations ORDER BY list_index ASC")
    fun getAll(): Observable<List<SavedLocationTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(items: List<SavedLocationTable>)

    @Delete
    fun delete(item: SavedLocationTable)

    @Insert
    fun insert(item: SavedLocationTable)

    /**
     * Возвращает текущий максимальный порядковый номер из сохраненных городов
     * @return - максимальный порядковый номер
     */
    @Query("SELECT list_index FROM saved_locations WHERE list_index = (SELECT MAX(list_index) FROM saved_locations)")
    fun getMaxListIndex(): List<Int>

    @Query("SELECT id FROM saved_locations")
    fun getAllIds(): List<Long>

    @Query("SELECT * FROM saved_locations LIMIT 1")
    fun getOneRow(): List<SavedLocationTable>
}