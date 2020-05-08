/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search


import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.PagedSearchRepository
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screen.common.BasePresenter
import com.nigtime.weatherapplication.screen.search.paging.PagedListLoader
import java.util.*


class SearchCityPresenter constructor(
    schedulerProvider: SchedulerProvider,
    private val pagedSearchRepository: PagedSearchRepository,
    private val pagedListLoader: PagedListLoader
) :
    BasePresenter<SearchCityView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "search"
        private const val MIN_QUERY_LENGTH = 1
    }

    fun onViewReady() {
        getView()?.showHintLayout()
    }

    fun onClickItem(searchCity: SearchCity) {
        if (searchCity.isWish) {
            getView()?.showToastAlreadyWish()
        } else {
            logger.d("insert, object = $searchCity")
            pagedSearchRepository.insert(searchCity)
                .subscribeOn(schedulerProvider.syncDatabase())
                .observeOn(schedulerProvider.ui())
                .subscribeAndHandleError() { insertedPosition ->
                    getView()?.setInsertedResult(insertedPosition)
                    getView()?.navigateToPreviousScreen()
                    logger.d("insert = ok")
                }
        }
    }

    fun onClickNavigationButton() {
        getView()?.navigateToPreviousScreen()
    }

    fun processInput(query: String) {
        if (query.length >= MIN_QUERY_LENGTH) {
            loadListQuery(query.toLowerCase(Locale.getDefault()))
        } else {
            performDispose()
            getView()?.showHintLayout()
        }
    }

    private fun loadListQuery(query: String) {
        getView()?.showProgressLayout()

        pagedListLoader.loadList(pagedSearchRepository, query)
            .subscribeAndHandleError() { pagedList ->
                getView()?.submitList(pagedList)
                if (pagedList.isNotEmpty()) {
                    getView()?.delayScrollToPosition(0)
                    getView()?.showListLayout()
                } else {
                    getView()?.showEmptyLayout()
                }
            }
    }
}


