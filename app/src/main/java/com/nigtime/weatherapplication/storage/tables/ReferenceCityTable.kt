/*
 * Сreated by Igor Pokrovsky. 2020/5/24
 */

package com.nigtime.weatherapplication.storage.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reference_cities")
data class ReferenceCityTable(
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "state_name")
    val stateName: String,
    @ColumnInfo(name = "country_name")
    val countryName: String,
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "lon")
    val lon: Double
)