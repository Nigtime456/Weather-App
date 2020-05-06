/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.screens.wishlist

import android.annotation.SuppressLint
import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.domain.repository.WishCitiesRepository
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.common.log.CustomLogger
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import io.reactivex.Scheduler


class WishCitiesPresenter constructor(
    schedulerProvider: SchedulerProvider,
    private val wishCitiesRepository: WishCitiesRepository,
    private val messageDispatcher: RxDelayedMessageDispatcher
) :
    BasePresenter<WishCitiesView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "wish_list"
        private val NO_POSITION = -1
    }

    private var insertedPosition = NO_POSITION
    private var isListEmpty: Boolean = false

    fun onClickItem(position: Int) {
        messageDispatcher.forceRun()
        getView()?.navigateToPageScreen(position)
    }

    fun onItemSwiped(item: WishCity, position: Int, items: MutableList<WishCity>) {
        //если уже что то стоит в очереди - удаляем
        messageDispatcher.forceRun()
        //оставляем возможность отмены

        getView()?.showUndoDeleteSnack()
        checkList(items, false)
        logger.d("delay delete obj = $item,pos = $position")
        //откладываем удаление из БД
        messageDispatcher.delayNextMessage(
            DeleteItemMessage(
                item,
                position,
                wishCitiesRepository,
                schedulerProvider.syncDatabase(),
                logger
            )
        )
    }

    fun onFragmentStop() {
        getView()?.hideUndoDeleteSnack()
        //выполняем отложенное удаление
        messageDispatcher.forceRun()
    }

    fun onItemsMoved(items: List<WishCity>) {
        logger.d("do replace")

        checkList(items, false)
        wishCitiesRepository.replaceAll(items)
            .subscribeOn(schedulerProvider.syncDatabase())
            .subscribeAndHandleError(false) {
                logger.d("items replaced")
                //nothing
            }
    }

    fun onClickUndoDelete() {
        //отменяем удаление
        messageDispatcher.cancelMessage()
        val message = messageDispatcher.getMessage()
        if (message is DeleteItemMessage) {
            logger.d("delete canceled obj = ${message.item},pos = ${message.position}")
            //отображаем элемент
            getView()?.showList()
            getView()?.insertItemToList(message.item, message.position)
            getView()?.scrollListToPosition(message.position)
        }
        messageDispatcher.clearMessage()
    }


    fun provideCities() {
        getView()?.showProgressBar()

        wishCitiesRepository.getCitiesList()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { list ->
                checkList(list, true)
            }
    }

    /**
     * Проверить лист и отобразить оотвествующие сообщения, если лист пустой
     *
     * @param submit - уведомить адаптер
     */
    private fun checkList(list: List<WishCity>, submit: Boolean) {
        logger.d("check list $list submit = $submit")
        isListEmpty = list.isEmpty()
        if (!isListEmpty && submit) {
            logger.d("submit list")
            getView()?.showList()
            getView()?.submitList(list)

            //Из за DiffUtil список может отрсисываваться долго и проглотит
            //переданную позицию.
            if (insertedPosition != NO_POSITION)
                getView()?.delayScrollListToPosition(insertedPosition)

        } else if (isListEmpty) {
            logger.d("list empty")
            getView()?.showMessageEmpty()
        }
    }

    fun onClickNavigationButton() {
        if (!isListEmpty) {
            getView()?.navigateToPreviousScreen()
        } else {
            getView()?.showPopupMessageEmptyList()
        }
    }

    fun onCityInsertedAt(position: Int) {
        insertedPosition = position
    }


    private class DeleteItemMessage(
        val item: WishCity,
        val position: Int,
        val repository: WishCitiesRepository,
        val scheduler: Scheduler,
        val logger: CustomLogger
    ) : Runnable {

        @SuppressLint("CheckResult")
        override fun run() {
            repository.delete(item)
                .subscribeOn(scheduler)
                .subscribe({ //nothing
                    logger.d("delete obj $item , position = $position")},{
                    logger.e(it,"error on remove")
                })
        }

    }
}