/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.domain.city

/**
 * Data класс для представления результата поиска города
 *
 * @param isWish - добавлен ли уже город в таблицу сохраненных
 * @param query - текст запроса поиска
 */
class SearchCity constructor(
    cityId: Long,
    name: String,
    stateName: String,
    countryName: String,
    val isWish: Boolean,
    val query: String
) : WishCity(cityId, NO_LIST_INDEX, name, stateName, countryName) {

    override fun toString(): String {
        return "SearchCityData{cityId=$cityId, listIndex=$listIndex, name='$name', stateName='$stateName', countryName='$countryName', isSelected='$isWish', query='$query'}"
    }
}