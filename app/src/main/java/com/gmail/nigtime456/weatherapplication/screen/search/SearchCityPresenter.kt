/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.search


import androidx.paging.PagedList
import com.gmail.nigtime456.weatherapplication.domain.location.PagedSearchRepository
import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter
import com.gmail.nigtime456.weatherapplication.screen.search.paging.PagedListLoader
import io.reactivex.rxkotlin.subscribeBy
import java.util.*


class SearchCityPresenter constructor(
    private val pagedSearchRepository: PagedSearchRepository,
    private val pagedListLoader: PagedListLoader
) :
    BasePresenter<SearchCityView>(TAG) {

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
            insertCity(searchCity)
        }
    }

    private fun insertCity(searchCity: SearchCity) {
        pagedSearchRepository.insert(searchCity)
            .subscribeBy(onSuccess = this::onCityInserted)
            .disposeOnDestroy()
    }

    private fun onCityInserted(insertedPosition: Int) {
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
            .subscribeBy(onNext = this::onNextQueryList)
            .disposeOnDestroy()
    }

    private fun onNextQueryList(pagedList: PagedList<SearchCity>) {
        getView()?.submitList(pagedList)
        if (pagedList.isNotEmpty()) {
            getView()?.delayScrollToPosition(0)
            getView()?.showListLayout()
        } else {
            getView()?.showEmptyLayout()
        }
    }
}


