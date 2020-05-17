/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherbitapp.uselles.storage.room.repository

import com.nigtime.weatherapplication.storage.service.ReferenceCitiesDao
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable
import io.reactivex.Single

//TODO 1) это все должно происходить в транзакции
//2) это должно быть в отдельном модуле, без зависимости в релизной сборки
//TODO USELESS
class RoomDictionaryWriter constructor(private val dictionaryDao: ReferenceCitiesDao) {

    fun isDictionaryWritten(): Single<Boolean> {
        return dictionaryDao
            .getOneRow()
            .map { it.isNotEmpty() }
    }


    fun writeDictionaryCity(dictionaryCity: ReferenceCitiesTable) {
        dictionaryDao.insert(dictionaryCity)
    }
}