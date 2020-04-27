/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity

import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.repository.SelectedCitySource
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
                .subscribeOn(schedulerProvider.io())
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
            loadQuery(text)
        } else {
            getView()?.showHint()
        }
    }

    private fun loadQuery(query: String) {
        if (selectedCitiesIds == null) {
            loadSelectedCities(query)
        } else {
            loadQueryList(query)
        }
    }

    private fun loadSelectedCities(query: String) {
        selectedCitySource.getAllIds()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { set ->
                selectedCitiesIds = set
                loadQueryList(query)
            }
    }

    private fun loadQueryList(query: String) {
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


