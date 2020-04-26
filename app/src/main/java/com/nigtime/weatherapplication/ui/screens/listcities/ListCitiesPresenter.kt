/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.screens.listcities

import android.annotation.SuppressLint
import com.nigtime.weatherapplication.db.data.SelectedCityData
import com.nigtime.weatherapplication.db.repository.SelectedCitySource
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.log.CustomLogger
import com.nigtime.weatherapplication.utility.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider
import io.reactivex.Scheduler


class ListCitiesPresenter constructor(
    schedulerProvider: SchedulerProvider,
    private val selectedCitySource: SelectedCitySource,
    private val messageDispatcher: RxDelayedMessageDispatcher
) :
    BasePresenter<ListCitiesView>(schedulerProvider, TAG) {


    companion object {
        private const val TAG = "list_cities"
    }

    fun onItemSwiped(item: SelectedCityData, position: Int, items: MutableList<SelectedCityData>) {
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
                selectedCitySource,
                schedulerProvider.io(),
                logger
            )
        )
    }

    fun onFragmentStop() {
        getView()?.hideUndoDeleteSnack()
        //выполняем отложенное удаление
        messageDispatcher.forceRun()
    }

    fun onItemsMoved(items: List<SelectedCityData>) {
        logger.d("do replace")
        checkList(items, false)
        selectedCitySource.replaceAll(items)
            .subscribeOn(schedulerProvider.io())
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
        } else {
            error("unknown message type = $message")
        }
        messageDispatcher.clearMessage()
    }


    fun provideCities() {
        getView()?.showProgressBar()

        selectedCitySource.getListSelectedCities()
            .subscribeOn(schedulerProvider.io())
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
    private fun checkList(list: List<SelectedCityData>, submit: Boolean) {
        logger.d("check list $list submit = $submit")
        if (list.isNotEmpty() && submit) {
            logger.d("submit list")
            getView()?.showList()
            getView()?.submitList(list)
        } else if (list.isEmpty()) {
            logger.d("list empty")
            getView()?.showMessageEmpty()
        }
    }

    private class DeleteItemMessage(
        val item: SelectedCityData,
        val position: Int,
        val source: SelectedCitySource,
        val scheduler: Scheduler,
        val logger: CustomLogger
    ) : Runnable {

        @SuppressLint("CheckResult")
        override fun run() {
            source.delete(item)
                .subscribeOn(scheduler)
                .subscribe {
                    //nothing
                    logger.d("delete obj $item , position = $position")
                }
        }

    }
}