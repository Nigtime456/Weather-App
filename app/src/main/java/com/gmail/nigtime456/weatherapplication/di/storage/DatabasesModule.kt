/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.storage

import android.content.Context
import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import com.gmail.nigtime456.weatherapplication.storage.service.ReferenceCitiesDao
import com.gmail.nigtime456.weatherapplication.storage.service.ReferenceCitiesDatabase
import com.gmail.nigtime456.weatherapplication.storage.service.SavedLocationsDao
import com.gmail.nigtime456.weatherapplication.storage.service.SavedLocationsDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabasesModule {

    @ApplicationScope
    @Provides
    fun provideSavedLocationsDao(database: SavedLocationsDatabase): SavedLocationsDao {
        return database.savedLocationsDao()
    }

    @ApplicationScope
    @Provides
    fun providesReferenceCitiesDao(database: ReferenceCitiesDatabase): ReferenceCitiesDao {
        return database.referenceCitiesDao()
    }


    @ApplicationScope
    @Provides
    fun provideSavedLocationsDatabase(context: Context): SavedLocationsDatabase {
        return SavedLocationsDatabase.getInstance(context)
    }

    @ApplicationScope
    @Provides
    fun provideReferenceCitiesDatabase(context: Context): ReferenceCitiesDatabase {
        return ReferenceCitiesDatabase.getInstance(context)
    }
}