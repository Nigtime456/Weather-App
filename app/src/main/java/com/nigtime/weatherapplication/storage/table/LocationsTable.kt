/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nigtime.weatherapplication.storage.table.TableConstants.COLUMN_LIST_INDEX

/**
 * Таблица выбранных городов.
 *
 * @param listIndex - позиция/порядковый номер в списках (Pager, ListCities)
 * @param id - внешний ключ на соотвествищий город из справочника [ReferenceCitiesTable]
 */

@Entity(
    tableName = TableConstants.TABLE_SAVED_LOCATION,
    indices = [
        Index(value = [COLUMN_LIST_INDEX], unique = true),
        Index(value = [TableConstants.COLUMN_ID], unique = true)
    ]
)
data class LocationsTable(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_LIST_INDEX)
    val listIndex: Int,
    @ColumnInfo(name = TableConstants.COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = TableConstants.COLUMN_CITY_NAME)
    val name: String,
    @ColumnInfo(name = TableConstants.COLUMN_STATE_NAME)
    val stateName: String,
    @ColumnInfo(name = TableConstants.COLUMN_COUNTRY_NAME)
    val countryName: String
)


