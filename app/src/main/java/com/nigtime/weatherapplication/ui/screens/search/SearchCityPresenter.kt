/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.search

import com.nigtime.weatherapplication.db.data.SearchCity
import com.nigtime.weatherapplication.db.repository.PagedSearchRepository
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.ui.screens.search.paging.PagedListLoader
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider


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

    fun onViewCreated() {
        getView()?.showHint()
    }

    fun onClickItem(searchCity: SearchCity) {
        if (searchCity.isWish) {
            getView()?.showMessageAlreadyWish()
        } else {
            logger.d("insert, object = $searchCity")
            pagedSearchRepository.insert(searchCity)
                .subscribeOn(schedulerProvider.syncDatabase())
                .observeOn(schedulerProvider.ui())
                .subscribeAndHandleError(false) {
                    //TODO set result с этим
                    getView()?.navigateToPreviousScreen()
                    logger.d("insert = ok")
                }
        }
    }

    fun onClickNavigationButton() {
        getView()?.navigateToPreviousScreen()
    }

    fun processInput(text: String) {
        if (text.length >= MIN_QUERY_LENGTH) {
            loadListQuery(text.toLowerCase())
        } else {
            getView()?.showHint()
        }
    }

    private fun loadListQuery(query: String) {
        getView()?.showProgressBar()
        pagedListLoader.loadList(pagedSearchRepository, query)
            .subscribeAndHandleError(false) { pagedList ->
                if (pagedList.isNotEmpty()) {
                    getView()?.submitList(pagedList)
                    getView()?.showList()
                } else {
                    getView()?.showMessageEmpty()
                }
            }
    }
}


