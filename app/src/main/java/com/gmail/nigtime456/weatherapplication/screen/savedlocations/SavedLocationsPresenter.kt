/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.gmail.nigtime456.weatherapplication.screen.savedlocations

import android.annotation.SuppressLint
import android.util.Log
import com.gmail.nigtime456.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.SavedLocationsRepository
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter
import io.reactivex.rxkotlin.subscribeBy


class SavedLocationsPresenter constructor(
    private val savedLocationsRepository: SavedLocationsRepository,
    private val messageDispatcher: RxDelayedMessageDispatcher
) : BasePresenter<SavedLocationView>(TAG) {

    companion object {
        private const val TAG = "saved_locations"
    }

    private var insertedPosition = 0
    private var mutableItems = mutableListOf<SavedLocation>()
    private var hasDrag = false

    override fun onAttach() {
        super.onAttach()
        provideLocations()
    }

    override fun onDetach() {
        super.onDetach()
        messageDispatcher.forceRun()
        getView()?.hideUndoDeleteSnack()

    }

    private fun provideLocations() {
        savedLocationsRepository.getLocationsOnce()
            .doOnSubscribe { getView()?.showProgressLayout() }
            .subscribeBy(onSuccess = this::onListLoaded)
            .disposeOnDestroy()
    }

    private fun onListLoaded(list: List<SavedLocation>) {
        mutableItems = list.toMutableList()

        if (mutableItems.isNotEmpty()) {
            getView()?.showListLayout()
            getView()?.submitList(mutableItems)
            //Из за DiffUtil список может отрсисываваться долго и проглотит
            //переданную позицию.
            getView()?.delayScrollToPosition(insertedPosition)
        } else {
            getView()?.showEmptyLayout()
        }
    }

    fun onItemSwiped(swiped: SavedLocation, position: Int) {
        //если есть ожидающиее сообщения, удаляем
        messageDispatcher.forceRun()
        //удаляем визуально
        mutableItems.removeAt(position)

        if (mutableItems.isEmpty()) {
            getView()?.showEmptyLayout()
        }

        //оставляем возможность отмены
        getView()?.showUndoDeleteSnack(messageDispatcher.durationMillis.toInt())

        //откладываем удаление из БД
        messageDispatcher.delayNextMessage(
            DeleteItemMessage(
                swiped,
                position,
                savedLocationsRepository
            )
        )
    }

    fun onItemsMoved(
        moved: SavedLocation,
        movedPosition: Int,
        target: SavedLocation,
        targetPosition: Int
    ) {
        hasDrag = true
        //перезаписываем индексы
        mutableItems[targetPosition] = moved.changeListIndex(targetPosition)
        mutableItems[movedPosition] = target.changeListIndex(movedPosition)
    }

    fun onMovementComplete() {
        Log.d("sas", "save changes")
        saveListChanges()
    }

    fun onItemClick(position: Int) {
        getView()?.setSelectionResult(position)
        getView()?.navigateToPreviousScreen()
    }

    fun onUndoDeleteClick() {
        val pendingMessage = messageDispatcher.cancelMessage()

        if (pendingMessage is DeleteItemMessage) {
            mutableItems.add(pendingMessage.position, pendingMessage.item)
            getView()?.showListLayout()
            getView()?.notifyItemInserted(pendingMessage.position)
            getView()?.scrollToPosition(pendingMessage.position)
        }

        //если между удаление и востановлением были движения,то
        //этот элемент не сохранится
        if (hasDrag) {
            hasDrag = false
            saveListChanges()
        }
    }

    private fun saveListChanges() {
        if (mutableItems.isNotEmpty()) {
            savedLocationsRepository.replaceAll(mutableItems)
                .subscribe()
        }
    }

    fun onNavigationButtonClick() {
        if (mutableItems.isNotEmpty()) {
            getView()?.navigateToPreviousScreen()
        } else {
            getView()?.showDialogAboutEmptyList()
        }
    }

    fun onMenuAddClick() {
        getView()?.navigateToSearchCityScreen()
    }

    fun onCityInserted(position: Int) {
        insertedPosition = position
    }

    private class DeleteItemMessage(
        val item: SavedLocation,
        val position: Int,
        val repository: SavedLocationsRepository
    ) : Runnable {

        @SuppressLint("CheckResult")
        override fun run() {
            repository.delete(item)
                .subscribe()
        }
    }
}