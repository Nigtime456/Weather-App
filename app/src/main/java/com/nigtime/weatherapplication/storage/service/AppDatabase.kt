/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nigtime.weatherapplication.storage.table.ReferenceCityTable
import com.nigtime.weatherapplication.storage.table.TableConstants
import com.nigtime.weatherapplication.storage.table.WishCityTable

@Database(
    entities = [ReferenceCityTable::class, WishCityTable::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun referenceCityDao(): ReferenceCityDao
    abstract fun wishCityDao(): WishCityDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                TableConstants.DATABASE_NAME
            )
                .build()
        }
    }

}