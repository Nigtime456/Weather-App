/*
 * Сreated by Igor Pokrovsky. 2020/5/24
 */

package com.gmail.nigtime456.weatherapplication.storage.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.nigtime456.weatherapplication.storage.table.ReferenceCityTable

@Database(
    entities = [ReferenceCityTable::class],
    version = 1,
    exportSchema = true
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