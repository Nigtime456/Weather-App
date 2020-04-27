/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity

import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.source.SelectedCitySource
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.ui.screens.searchcity.paging.PagingListLoader
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider


class SearchCityPresenter constructor(
    schedulerProvider: SchedulerProvider,
    private val pagingListLoader: PagingListLoader,
    private val selectedCitySource: SelectedCitySource
) :
    BasePresenter<SearchCityView>(schedulerProvider, TAG) {

    private var selectedCitiesIds: Set<Long>? = null

    companion object {
        private const val TAG = "search"
        private const val MIN_QUERY_LENGTH = 1
    }

    fun onViewCreated() {
        getView()?.showHint()
    }

    fun onClickItem(searchCityData: SearchCityData) {
        if (searchCityData.isSelected) {
            getView()?.showMessageAlreadySelected()
        } else {
            logger.d("insert, object = $searchCityData")
            selectedCitySource.insert(searchCityData)
                .subscribeOn(schedulerProvider.syncDatabase())
                .observeOn(schedulerProvider.ui())
                .subscribeAndHandleError(false) {
                    logger.d("insert = ok")
                    getView()?.navigateToPreviousScreen()
                }
        }
    }

    fun onClickNavigationButton() {
        getView()?.navigateToPreviousScreen()
    }

    fun processInput(text: String) {
        if (text.length >= MIN_QUERY_LENGTH) {
            performQuery(text)
        } else {
            getView()?.showHint()
        }
    }

    private fun performQuery(query: String) {
        if (selectedCitiesIds == null) {
            loadSelectedIds(query)
        } else {
            loadListQuery(query)
        }
    }

    private fun loadSelectedIds(query: String) {
        selectedCitySource.getAllIds()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { set ->
                selectedCitiesIds = set
                loadListQuery(query)
            }
    }

    private fun loadListQuery(query: String) {
        getView()?.showProgressBar()
        pagingListLoader.load(query, selectedCitiesIds!!)
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


