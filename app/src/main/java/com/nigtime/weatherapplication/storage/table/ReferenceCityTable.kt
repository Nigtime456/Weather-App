/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник городов, доступных к прогнозу
 *
 * @param cityId - ID города
 * @param name - имя города
 * @param stateName - штат
 * @param countryName - имя страны
 */
@Entity(tableName = TableConstants.TABLE_GEO_CITY)
data class ReferenceCityTable(
    @PrimaryKey
    @ColumnInfo(name = TableConstants.COLUMN_CITY_ID)
    val cityId: Long,
    @ColumnInfo(name = TableConstants.COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = TableConstants.COLUMN_STATE_NAME)
    val stateName: String,
    @ColumnInfo(name = TableConstants.COLUMN_COUNTRY_NAME)
    val countryName: String
)
