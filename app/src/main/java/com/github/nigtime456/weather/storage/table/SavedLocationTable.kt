/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.storage.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Таблица выбранных городов.
 *
 * @param listIndex - позиция/порядковый номер в списках
 */

@Entity(tableName = "saved_locations")
data class SavedLocationTable(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "lon")
    val lon: Double,
    @ColumnInfo(name = "list_index")
    val listIndex: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "state")
    val state: String,
    @ColumnInfo(name = "country")
    val country: String
)


