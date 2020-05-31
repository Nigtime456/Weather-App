/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search


import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.domain.repository.SearchRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class SearchPresenter @Inject constructor(
    private val view: SearchContract.View,
    private val searchRepository: SearchRepository
) : SearchContract.Presenter {

    companion object {
        private const val MIN_QUERY_LENGTH = 2
    }

    private val compositeDisposable = CompositeDisposable()

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun queryChanges(query: CharSequence) {
        compositeDisposable.clear()
        if (query.length >= MIN_QUERY_LENGTH) {
            provideCities(query.toString())
            view.showListLayout()
        } else {
            view.showEmptyLayout()
        }
    }

    private fun provideCities(query: String) {
        compositeDisposable += searchRepository.searchCities(query)
            .doOnSubscribe { view.showProgressLayout() }
            .subscribeBy { list ->
                if (list.isNotEmpty()) {
                    view.showSearchResult(list, 0)
                } else {
                    view.showNotFoundLayout()
                }
            }
    }

    override fun clickItem(city: SearchCity) {
        if (city.isSaved) {
            view.showMessageAlreadySaved()
        } else {
            insertCity(city)
        }
    }


    private fun insertCity(searchCity: SearchCity) {
        compositeDisposable += searchRepository.insert(searchCity)
            .subscribeBy { insertedPosition ->
                view.setInsertResult(insertedPosition)
                view.finishView()
            }
    }

}


