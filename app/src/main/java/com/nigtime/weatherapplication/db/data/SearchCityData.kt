/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.data

/**
 * Data класс для представления результата поиска города
 *
 * @param isSelected - добавлен ли уже город в таблицу сохраненных
 * @param query - текст запроса поиска
 */
class SearchCityData constructor(
    cityId: Long,
    name: String,
    stateName: String,
    countryName: String,
    val isSelected: Boolean,
    val query: String
) : SelectedCityData(cityId, NO_LIST_INDEX, name, stateName, countryName) {

    override fun toString(): String {
        return "SearchCityData{cityId=$cityId, listIndex=$listIndex, name='$name', stateName='$stateName', countryName='$countryName', isSelected='$isSelected', query='$query'}"
    }
}