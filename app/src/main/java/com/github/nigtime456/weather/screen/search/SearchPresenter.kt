/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.github.nigtime456.weather.screen.search

import com.github.nigtime456.weather.data.location.SearchCity
import com.github.nigtime456.weather.data.repository.SearchRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val view: SearchContract.View,
    private val searchRepository: SearchRepository
) : SearchContract.Presenter {

    private companion object {
        const val MIN_QUERY_LENGTH = 2
        const val TAG = "search"
    }

    private val compositeDisposable = CompositeDisposable()

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun queryChanges(query: String) {
        compositeDisposable.clear()
        if (query.length >= MIN_QUERY_LENGTH) {
            provideCities(query)
        } else {
            view.showEmptyLayout()
        }
    }

    override fun onListUpdated() {
        view.scrollToPosition(0)
    }

    private fun provideCities(query: String) {
        compositeDisposable += searchRepository.searchCities(query)
            .doOnSubscribe { view.showProgressLayout() }
            .subscribeBy { list ->
                if (list.isNotEmpty()) {
                    view.showListLayout()
                    view.showSearchResult(list)
                } else {
                    view.showNotFoundLayout()
                }
            }
    }

    override fun clickItem(item: SearchCity) {
        if (item.isSaved) {
            view.showMessageAlreadySaved()
        } else {
            insertCity(item)
        }
    }

    private fun insertCity(searchCity: SearchCity) {
        compositeDisposable += searchRepository.insert(searchCity)
            .subscribeBy { insertedPosition ->
                Timber.tag(TAG).d("insert at = $insertedPosition")
                view.setInsertResult(insertedPosition)
                view.finishView()
            }
    }

}


