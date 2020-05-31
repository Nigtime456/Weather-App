/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations

import android.annotation.SuppressLint
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.LocationsRepository
import com.gmail.nigtime456.weatherapplication.tools.rx.RxDelayedMessageDispatcher
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class LocationsPresenter @Inject constructor(
    private val view: LocationsContract.View,
    private val locationsRepository: LocationsRepository,
    private val messageDispatcher: RxDelayedMessageDispatcher
) : LocationsContract.Presenter {

    private companion object {
        const val NO_POSITION = -1
    }

    private val compositeDisposable = CompositeDisposable()
    private var insertedPosition = 0
    private var mutableItems = mutableListOf<SavedLocation>()

    override fun loadLocations() {
        compositeDisposable += locationsRepository.getLocations()
            .take(1)
            .doOnSubscribe { view.showProgressLayout() }
            .subscribeBy { list ->
                mutableItems = list.toMutableList()
                if (mutableItems.isNotEmpty()) {
                    view.showListLayout()
                    showLocations()
                } else {
                    view.showEmptyLayout()
                }
            }
    }


    private fun showLocations() {
        if (insertedPosition != NO_POSITION) {
            view.showLocations(mutableItems, insertedPosition)
        } else {
            view.showLocations(mutableItems)
        }
    }

    override fun stop() {
        insertedPosition = NO_POSITION
        view.hideUndoDeleteSnack()
        messageDispatcher.forceRun()
        compositeDisposable.clear()
    }

    override fun swipeItem(swiped: SavedLocation, position: Int) {
        //если есть ожидающиее сообщения, удаляем
        messageDispatcher.forceRun()
        //удаляем визуально
        mutableItems.removeAt(position)

        if (mutableItems.isEmpty()) {
            view.showEmptyLayout()
        }

        //оставляем возможность отмены
        view.showUndoDeleteSnack(messageDispatcher.durationMillis.toInt())

        //откладываем удаление из БД
        messageDispatcher.delayNextMessage(
            DeleteItemMessage(
                swiped,
                position,
                locationsRepository
            )
        )
    }

    override fun moveItems(
        moved: SavedLocation,
        movedPosition: Int,
        target: SavedLocation,
        targetPosition: Int
    ) {
        //перезаписываем индексы
        mutableItems[targetPosition] = moved
        mutableItems[movedPosition] = target
    }

    override fun completeMovement() {
        saveListChanges()
    }

    private fun saveListChanges() {
        if (mutableItems.isNotEmpty()) {
            locationsRepository.replaceAll(mutableItems)
                .subscribe()
        }
    }

    override fun clickItem(position: Int) {
        view.setSelectResult(position)
        view.finishView()
    }

    override fun clickUndoDelete() {
        val pendingMessage = messageDispatcher.cancelMessage()

        if (pendingMessage is DeleteItemMessage) {
            mutableItems.add(pendingMessage.position, pendingMessage.item)
            view.showListLayout()
            view.notifyItemInserted(pendingMessage.position)
            view.scrollToPosition(pendingMessage.position)
        }

        //если между удаление и востановлением были движения,то
        //этот элемент не сохранится. Сохраняем лист.
        saveListChanges()
    }

    override fun clickAddCity() {
        view.showSearchScreen()
    }

    override fun backPressed() {
        if (mutableItems.isNotEmpty()) {
            view.finishView()
        } else {
            view.showMessageAboutEmptyList()
        }
    }

    override fun onCityInserted(position: Int) {
        insertedPosition = position
    }

    private class DeleteItemMessage(
        val item: SavedLocation,
        val position: Int,
        val repository: LocationsRepository
    ) : Runnable {

        @SuppressLint("CheckResult")
        override fun run() {
            repository.delete(item)
                .subscribe()
        }
    }
}