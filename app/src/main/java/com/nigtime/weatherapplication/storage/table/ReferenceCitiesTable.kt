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
@Entity(tableName = TableConstants.TABLE_REFERENCE_CITIES)
data class ReferenceCitiesTable(
    @PrimaryKey
    @ColumnInfo(name = TableConstants.COLUMN_ID)
    val cityId: Long,
    @ColumnInfo(name = TableConstants.COLUMN_CITY_NAME)
    val name: String,
    @ColumnInfo(name = TableConstants.COLUMN_STATE_NAME)
    val stateName: String,
    @ColumnInfo(name = TableConstants.COLUMN_COUNTRY_NAME)
    val countryName: String
)
