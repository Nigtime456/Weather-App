/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.storage.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nigtime.weatherapplication.storage.table.LocationTable
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable
import com.nigtime.weatherapplication.storage.table.TableConstants

@Database(
    entities = [ReferenceCitiesTable::class, LocationTable::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun referenceCitiesDao(): ReferenceCitiesDao
    abstract fun savedLocationsDao(): SavedLocationsDao

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