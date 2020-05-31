/*
 * Сreated by Igor Pokrovsky. 2020/5/24
 */

package com.gmail.nigtime456.weatherapplication.storage.service

import androidx.room.Dao
import androidx.room.Query
import com.gmail.nigtime456.weatherapplication.storage.table.ReferenceCityTable

@Dao
interface ReferenceCitiesDao {
    @Query("SELECT * FROM reference_cities WHERE name LIKE :name ORDER BY name ASC LIMIT 50")
    fun searchByName(name: String): List<ReferenceCityTable>
}