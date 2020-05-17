/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.savedlocations

import android.annotation.SuppressLint
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.location.SavedLocationRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Scheduler


class SavedLocationsPresenter constructor(
    schedulerProvider: SchedulerProvider,
    private val savedLocationRepository: SavedLocationRepository,
    private val messageDispatcher: RxDelayedMessageDispatcher
) :
    BasePresenter<SavedLocationView>(schedulerProvider, TAG) {

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
        getView()?.showProgressLayout()

        savedLocationRepository.getLocations()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(onResult = this::checkList)
    }

    private fun checkList(list: List<SavedLocation>) {
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
                savedLocationRepository,
                schedulerProvider.syncDatabase()
            )
        )
    }

    fun saveListChanges() {
        if (mutableItems.isNotEmpty()) {
            savedLocationRepository.replaceAll(mutableItems)
                .subscribeOn(schedulerProvider.syncDatabase())
                .subscribeAndHandleError {
                    /*nothing */
                }
        }
    }

    fun onItemsMoved(
        moved: SavedLocation,
        movedPosition: Int,
        target: SavedLocation,
        targetPosition: Int
    ) {
        hasDrag = true
        //перезаписываем индексы
        mutableItems[targetPosition] = moved
        mutableItems[movedPosition] = target
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
        val repository: SavedLocationRepository,
        val scheduler: Scheduler
    ) : Runnable {

        @SuppressLint("CheckResult")
        override fun run() {
            repository.delete(item)
                .subscribeOn(scheduler)
                .subscribe {
                    //nothing
                }
        }
    }
}