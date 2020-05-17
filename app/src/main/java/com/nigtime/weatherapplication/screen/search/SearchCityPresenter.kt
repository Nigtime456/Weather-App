/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search


import androidx.paging.PagedList
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.location.PagedSearchRepository
import com.nigtime.weatherapplication.domain.location.SearchCity
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

    override fun onAttach() {
        super.onAttach()
        getView()?.showHintLayout()
    }

    fun onItemClick(searchCity: SearchCity) {
        if (searchCity.isSaved) {
            getView()?.showToastAlreadyAdded()
        } else {
            pagedSearchRepository.insert(searchCity)
                .subscribeOn(schedulerProvider.syncDatabase())
                .observeOn(schedulerProvider.ui())
                .subscribeAndHandleError(onResult = this::onInsertResult)
        }
    }

    private fun onInsertResult(insertedPosition: Int) {
        getView()?.setInsertionResult(insertedPosition)
        getView()?.navigateToPreviousScreen()
    }

    fun onNavigationButtonClick() {
        getView()?.navigateToPreviousScreen()
    }

    fun processTextInput(query: String) {
        performDispose()
        if (query.length >= MIN_QUERY_LENGTH) {
            loadQueryList(query.toLowerCase(Locale.getDefault()))
        } else {
            getView()?.showHintLayout()
        }
    }

    private fun loadQueryList(query: String) {
        getView()?.showProgressLayout()
        pagedListLoader.loadList(pagedSearchRepository, query)
            .subscribeAndHandleError(onNext = this::onListLoaded)
    }

    private fun onListLoaded(pagedList: PagedList<SearchCity>) {
        getView()?.submitList(pagedList)
        if (pagedList.isNotEmpty()) {
            getView()?.delayScrollToPosition(0)
            getView()?.showListLayout()
        } else {
            getView()?.showEmptyLayout()
        }
    }
}


