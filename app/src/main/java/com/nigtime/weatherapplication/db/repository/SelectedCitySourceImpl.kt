/*
 * Сreated by Igor Pokrovsky. 2020/4/25 
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.CityForForecastData
import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.data.SelectedCityData
import com.nigtime.weatherapplication.db.service.GeoCityDao
import com.nigtime.weatherapplication.db.service.SelectedCityDao
import com.nigtime.weatherapplication.db.tables.GeoCityTable
import com.nigtime.weatherapplication.db.tables.SelectedCityTable
import io.reactivex.Completable
import io.reactivex.Single

class SelectedCitySourceImpl constructor(
    private val geoCityDao: GeoCityDao,
    private val selectedCityDao: SelectedCityDao
) : SelectedCitySource {

    override fun getListSelectedCities(): Single<List<SelectedCityData>> {
        return Single.fromCallable { selectedCityDao.getAllAsSingle() }
            .map(this::getCityDataByIds)

    }

    override fun getListCityForForecast(): Single<List<CityForForecastData>> {
        return Single.fromCallable { selectedCityDao.getAllIds() }
            .map { ids ->
                ids.map { cityId ->
                    val cityName = geoCityDao.getNameById(cityId)
                    CityForForecastData(cityId, cityName)
                }
            }
    }

    override fun getAllIds(): Single<Set<Long>> {
        return Single.fromCallable { selectedCityDao.getAllIds() }
            .map { sourceList -> sourceList.toSet() }
    }

    override fun hasCities(): Single<Boolean> {
        return Single.fromCallable { selectedCityDao.getOneRow() }
            .map { sourceList -> sourceList.isNotEmpty() }
    }

    override fun insert(item: SearchCityData): Completable {
        return Single.fromCallable { selectedCityDao.getMaxListIndex() }
            .map(this::getNewIndexFromList)
            .map { newIndex -> item.toTableObject(newIndex) }
            .map(selectedCityDao::insert)
            .ignoreElement()
    }

    override fun delete(item: SelectedCityData): Completable {
        return Completable.fromAction { selectedCityDao.delete(item.toTableObject()) }
    }

    override fun replaceAll(items: List<SelectedCityData>): Completable {
        return Single.just(items)
            .map { list -> list.mapIndexed { index, city -> city.toTableObject(index) } }
            .doOnSuccess(selectedCityDao::insertAndReplaceAll)
            .ignoreElement()
    }

    private fun getNewIndexFromList(listWithIndex: List<Int>): Int {
        require(listWithIndex.size <= 1) { "list must be contained one or none indexes" }
        return if (listWithIndex.isEmpty()) 0 else listWithIndex.first().inc()
    }

    /**
     * Получает список [SelectedCityTable] и по каждому индентификатору получает
     * данные со справочника [GeoCityTable]
     */
    private fun getCityDataByIds(sourceList: List<SelectedCityTable>): List<SelectedCityData> {
        return sourceList.map { tableItem ->
            geoCityDao.getById(tableItem.cityId)
                .toCityData(tableItem.listIndex)
        }
    }

    private fun SelectedCityData.toTableObject(newIndex: Int): SelectedCityTable {
        return SelectedCityTable(cityId, newIndex)
    }

    private fun SelectedCityData.toTableObject(): SelectedCityTable {
        return SelectedCityTable(cityId, listIndex)
    }

    private fun GeoCityTable.toCityData(index: Int): SelectedCityData {
        return SelectedCityData(cityId, index, name, stateName, countryName)
    }
}