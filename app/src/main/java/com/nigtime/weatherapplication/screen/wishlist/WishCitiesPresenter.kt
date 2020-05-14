/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist

import android.annotation.SuppressLint
import android.util.Log
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.WishCitiesRepository
import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Observable
import io.reactivex.Scheduler


class WishCitiesPresenter constructor(
    schedulerProvider: SchedulerProvider,
    private val wishCitiesRepository: WishCitiesRepository,
    private val messageDispatcher: RxDelayedMessageDispatcher
) :
    BasePresenter<WishCitiesView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "wish_list"
    }

    private var insertedPosition = 0
    private var mutableItems = mutableListOf<WishCity>()
    private var hasDrag = false

    override fun onShowView() {
        super.onShowView()
        provideCities()
    }

    override fun onHideView() {
        super.onHideView()
        removePendingMessage()
    }

    fun onViewStop() {
        removePendingMessage()
    }

    private fun removePendingMessage() {
        messageDispatcher.forceRun()
        getView()?.hideUndoDeleteSnack()
    }

    override fun onDetach() {
        super.onDetach()
        messageDispatcher.forceRun()
        getView()?.hideUndoDeleteSnack()
    }

    fun onItemSwiped(swiped: WishCity, position: Int) {
        //если есть ожидающиее удаляения сообщения, удаляем
        messageDispatcher.forceRun()


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
                wishCitiesRepository,
                schedulerProvider.syncDatabase()
            )
        )
    }

    fun saveListChanges() {
        if (mutableItems.isNotEmpty()) {
            wishCitiesRepository.replaceAll(mutableItems)
                .subscribeOn(schedulerProvider.syncDatabase())
                .subscribeAndHandleError(false) {
                    logger.d("items replaced")
                    //nothing
                }
        }
    }

    fun onItemsMoved(moved: WishCity, movedPosition: Int, target: WishCity, targetPosition: Int) {
        hasDrag = true
        //перезаписываем индексы
        mutableItems[targetPosition] = moved
        mutableItems[movedPosition] = target
    }


    fun onClickItem(position: Int) {
        getView()?.setSelectedResult(position)
        getView()?.navigateToPreviousScreen()
    }

    fun onClickUndoDelete() {
        val pendingMessage = messageDispatcher.cancelMessage()

        if (pendingMessage is DeleteItemMessage) {
            Log.d("sas","restore , pos = ${pendingMessage.position}")
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

    fun provideCities() {
        getView()?.showProgressLayout()

        wishCitiesRepository.getListCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { list ->
                checkList(list)
            }
    }


    fun onClickNavigationButton() {
        if (mutableItems.isNotEmpty()) {
            getView()?.navigateToPreviousScreen()
        } else {
            getView()?.showDialogEmptyList()
        }
    }

    fun onClickMenuAdd() {
        getView()?.navigateToSearchCityScreen()
    }

    fun observeInsertNewCity(insertObservable: Observable<Int>) {
        insertObservable.subscribe { position -> insertedPosition = position }
            .disposeOnDetach()
    }

    private fun checkList(list: List<WishCity>) {
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


    private class DeleteItemMessage(
        val item: WishCity,
        val position: Int,
        val repository: WishCitiesRepository,
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