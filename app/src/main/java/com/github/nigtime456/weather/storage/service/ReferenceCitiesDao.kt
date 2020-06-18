/*
 * Сreated by Igor Pokrovsky. 2020/5/24
 */

package com.github.nigtime456.weather.storage.service

import androidx.room.Dao
import androidx.room.Query
import com.github.nigtime456.weather.storage.table.ReferenceCityTable

@Dao
interface ReferenceCitiesDao {
    @Query("SELECT * FROM reference_cities WHERE name LIKE :name ORDER BY name ASC LIMIT 50")
    fun searchByName(name: String): List<ReferenceCityTable>
}