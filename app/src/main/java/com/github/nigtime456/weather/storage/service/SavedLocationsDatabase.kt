/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.storage.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.nigtime456.weather.storage.table.SavedLocationTable
import com.github.nigtime456.weather.utils.AppExecutors

@Database(
    entities = [SavedLocationTable::class],
    version = 1,
    exportSchema = false
)
abstract class SavedLocationsDatabase : RoomDatabase() {

    abstract fun savedLocationsDao(): SavedLocationsDao

    companion object {
        private const val DATABASE_NAME = "saved_locations.db"

        fun getInstance(context: Context): SavedLocationsDatabase {
            return Room.databaseBuilder(context, SavedLocationsDatabase::class.java, DATABASE_NAME)
                .setQueryExecutor(AppExecutors.dataBaseExecutor)
                .setTransactionExecutor(AppExecutors.dataBaseExecutor)
                .build()
        }
    }

}