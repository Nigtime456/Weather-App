/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherbitapp.uselles.storage.room.repository

import com.nigtime.weatherapplication.db.service.GeoCityDao
import com.nigtime.weatherapplication.db.tables.GeoCityTable
import io.reactivex.Single

//TODO USELESS
class RoomDictionaryWriter constructor(private val dictionaryDao: GeoCityDao) {

    fun isDictionaryWritten(): Single<Boolean> {
        return dictionaryDao
            .getOneRow()
            .map { it.isNotEmpty() }
    }


    fun writeDictionaryCity(dictionaryCity: GeoCityTable) {
        dictionaryDao.insert(dictionaryCity)
    }
}