/*
 * Сreated by Igor Pokrovsky. 2020/5/24
 */

package com.github.nigtime456.weather.storage.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.nigtime456.weather.storage.table.ReferenceCityTable

@Database(
    entities = [ReferenceCityTable::class],
    version = 1,
    exportSchema = false
)
abstract class ReferenceCitiesDatabase : RoomDatabase() {

    abstract fun referenceCitiesDao(): ReferenceCitiesDao

    companion object {
        private const val DATABASE_NAME = "reference_cities.db"

        fun getInstance(context: Context): ReferenceCitiesDatabase {
            return Room.databaseBuilder(context, ReferenceCitiesDatabase::class.java, DATABASE_NAME)
                .createFromAsset("reference_cities.db")
                .build()
        }
    }
}