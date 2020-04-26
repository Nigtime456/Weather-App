/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nigtime.weatherapplication.db.tables.GeoCityTable
import com.nigtime.weatherapplication.db.tables.SelectedCityTable
import com.nigtime.weatherapplication.db.tables.TableConstants

@Database(
    entities = [GeoCityTable::class, SelectedCityTable::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun geoCityDao(): GeoCityDao
    abstract fun selectedCityDao(): SelectedCityDao

    object Instance {
        private lateinit var db: AppDatabase

        fun get(context: Context): AppDatabase {
            if (!Instance::db.isInitialized) {
                db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    TableConstants.DATABASE_NAME
                )
                    .build()
            }
            return db
        }
    }
}