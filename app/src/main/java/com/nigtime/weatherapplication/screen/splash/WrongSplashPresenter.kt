/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash

import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.BasePresenter
import com.nigtime.weatherapplication.trash.CitiesMarshallingHelper
import com.nigtime.weatherbitapp.uselles.storage.room.repository.RoomDictionaryWriter
import io.reactivex.Single
import java.util.zip.ZipInputStream

//TODO Должен копировать базу данных
//TODO нужно переписать, вынести в отдельный модуль
class WrongSplashPresenter :
    BasePresenter<SplashView>(App.INSTANCE.appContainer.schedulerProvider, TAG) {
    private val referenceCityDao = App.INSTANCE.appContainer.referenceCitiesDao

    companion object {
        private const val TAG = "wrong_splash"
    }

    override fun onAttach() {
        super.onAttach()
        checkReferenceCities()
    }

    private fun checkReferenceCities() {
        RoomDictionaryWriter(referenceCityDao)
            .isDictionaryWritten()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::onCheckSuccess, this::onStreamError)
            .disposeOnDestroy()
    }

    private fun onCheckSuccess(result: Boolean) {
        if (result) {
            onWrithingCompleted()
        } else {
            writeCities()
        }
    }

    private fun writeCities() {
        val context = App.INSTANCE
        val marshallingHelper = CitiesMarshallingHelper()
        val dictionaryWriter = RoomDictionaryWriter(referenceCityDao)
        Single.just(context.assets.open("cities_full.zip"))
            .map { ZipInputStream(it) }
            .doOnSuccess { it.nextEntry }
            .map { it.bufferedReader() }
            .toFlowable()
            .flatMap { marshallingHelper.readFromReader(it) }
            .doOnNext { dictionaryWriter.writeDictionaryCity(it) }
            .ignoreElements()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::onWrithingCompleted, this::rethrowError)
            .disposeOnDestroy()
    }

    private fun onWrithingCompleted() {
        App.INSTANCE.appContainer.savedLocationsRepository.hasLocations()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError {
                getView()?.finishSplash()
                if (it)
                    getView()?.navigateToLocationPagesScreen()
                else
                    getView()?.navigateToSavedLocationsScreen()
            }
    }

}