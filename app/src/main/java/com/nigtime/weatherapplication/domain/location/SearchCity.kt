/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.domain.location

/**
 * Data класс для представления результата поиска города
 *
 *
 * @param isSaved - добавлен ли уже город в таблицу сохраненных
 * @param query - текст запроса поиска
 */
data class SearchCity constructor(
    val cityId: Long,
    val name: String,
    val stateName: String,
    val countryName: String,
    val isSaved: Boolean,
    val query: String
) {

    fun getStateAndCounty(): CharSequence {
        return if (stateName.isNotEmpty()) {
            if (countryName.isNotEmpty()) {
                "$countryName, $stateName"
            } else {
                stateName
            }
        } else {
            countryName
        }
    }
}