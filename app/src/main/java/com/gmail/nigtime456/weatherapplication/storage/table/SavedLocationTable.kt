/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.storage.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Таблица выбранных городов.
 *
 * @param listIndex - позиция/порядковый номер в списках (Pager, ListCities)
 */

@Entity(
    tableName = "saved_locations",
    indices = [
        Index(value = ["list_index"], unique = true),
        Index(value = ["city_id"], unique = true)
    ]
)
data class SavedLocationTable(
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val id: Long,
    @ColumnInfo(name = "list_index")
    val listIndex: Int,
    @ColumnInfo(name = "city_name")
    val name: String,
    @ColumnInfo(name = "city_state_name")
    val stateName: String,
    @ColumnInfo(name = "city_country_name")
    val countryName: String
)


