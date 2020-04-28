/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherbitapp.uselles.storage.room.repository

import com.nigtime.weatherapplication.db.entity.ReferenceCityTable
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import io.reactivex.Single

//TODO USELESS
class RoomDictionaryWriter constructor(private val dictionaryDao: ReferenceCityDao) {

    fun isDictionaryWritten(): Single<Boolean> {
        return dictionaryDao
            .getOneRow()
            .map { it.isNotEmpty() }
    }


    fun writeDictionaryCity(dictionaryCity: ReferenceCityTable) {
        dictionaryDao.insert(dictionaryCity)
    }
}