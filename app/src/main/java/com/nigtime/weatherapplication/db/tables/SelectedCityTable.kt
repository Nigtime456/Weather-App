/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.tables

import androidx.room.*
import com.nigtime.weatherapplication.db.tables.TableConstants.COLUMN_LIST_INDEX

/**
 * Таблица выбранных городов.
 *
 * @param listIndex - позиция/порядковый номер в списках (Pager, ListCities)
 * @param cityId - внешний ключ на соотвествищий город из справочника [GeoCityTable]
 */

@Entity(
    tableName = TableConstants.TABLE_SELECTED_CITY,
    indices = [
        Index(value = [COLUMN_LIST_INDEX], unique = true),
        Index(value = [TableConstants.COLUMN_CITY_ID], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = GeoCityTable::class,
            parentColumns = [TableConstants.COLUMN_CITY_ID],
            childColumns = [TableConstants.COLUMN_CITY_ID]
        )
    ]
)
data class SelectedCityTable(
    @ColumnInfo(name = TableConstants.COLUMN_CITY_ID)
    val cityId: Long,
    @PrimaryKey
    @ColumnInfo(name = COLUMN_LIST_INDEX)
    val listIndex: Int
)


