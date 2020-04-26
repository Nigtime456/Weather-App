/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.service

import androidx.room.*
import com.nigtime.weatherapplication.db.tables.SelectedCityTable
import io.reactivex.Single


@Dao
interface SelectedCityDao {

    @Query("SELECT *FROM selected_city ORDER BY list_index ASC")
    fun getAllAsSingle(): List<SelectedCityTable>

    @Query("SELECT city_id FROM selected_city")
    fun getAllIds(): List<Long>

    @Insert
    fun insert(city: SelectedCityTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplaceAll(items: List<SelectedCityTable>)

    @Delete
    fun delete(city: SelectedCityTable)

    /**
     * Возвращает текущий максимальный порядковый номер из сохраненных городов
     * @return - максимальный порядковый номер
     */
    @Query("SELECT list_index FROM selected_city WHERE list_index = (SELECT MAX(list_index) FROM selected_city)")
    fun getMaxListIndex(): List<Int>

    @Query("SELECT * FROM selected_city LIMIT 1")
    fun getOneRow(): List<SelectedCityTable>

}