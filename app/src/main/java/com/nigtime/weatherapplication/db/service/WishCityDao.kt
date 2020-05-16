/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.service

import androidx.room.*
import com.nigtime.weatherapplication.db.table.WishCityTable
import io.reactivex.Flowable


@Dao
interface WishCityDao {

    @Query("SELECT *FROM wish_list ORDER BY list_index ASC")
    fun getAll(): List<WishCityTable>

    @Query("SELECT city_id FROM wish_list")
    fun getAllIds(): List<Long>

    @Query("SELECT city_id FROM wish_list")
    fun getAllIdsAsFlow(): Flowable<List<Long>>

    @Query("SELECT city_name FROM reference_city WHERE city_id == :id")
    fun getCityName(id: Long): String

    @Insert
    fun insert(city: WishCityTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplaceAll(items: List<WishCityTable>)

    @Delete
    fun delete(city: WishCityTable)

    /**
     * Возвращает текущий максимальный порядковый номер из сохраненных городов
     * @return - максимальный порядковый номер
     */
    @Query("SELECT list_index FROM wish_list WHERE list_index = (SELECT MAX(list_index) FROM wish_list)")
    fun getMaxListIndex(): List<Int>

    @Query("SELECT * FROM wish_list LIMIT 1")
    fun getOneRow(): List<WishCityTable>
}