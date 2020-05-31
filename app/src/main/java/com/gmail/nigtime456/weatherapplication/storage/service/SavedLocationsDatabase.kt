/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.storage.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.nigtime456.weatherapplication.tools.AppExecutors
import com.gmail.nigtime456.weatherapplication.storage.table.SavedLocationTable

@Database(
    entities = [SavedLocationTable::class],
    version = 1,
    exportSchema = true
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