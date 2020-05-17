/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.table

import androidx.room.*
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
    ],
    foreignKeys = [
        ForeignKey(
            entity = ReferenceCitiesTable::class,
            parentColumns = [TableConstants.COLUMN_ID],
            childColumns = [TableConstants.COLUMN_ID]
        )
    ]
)
data class LocationTable(
    @ColumnInfo(name = TableConstants.COLUMN_ID)
    val id: Long,
    @PrimaryKey
    @ColumnInfo(name = COLUMN_LIST_INDEX)
    val listIndex: Int
)


