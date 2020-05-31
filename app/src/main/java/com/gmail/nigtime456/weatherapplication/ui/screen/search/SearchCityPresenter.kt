/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search


import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.domain.repository.PagedSearchRepository
import com.jakewharton.rxbinding3.InitialValueObservable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchCityPresenter @Inject constructor(
    private val view: SearchContract.View,
    private val pagedSearchRepository: PagedSearchRepository
) : SearchContract.Presenter {

    companion object {
        private const val MIN_QUERY_LENGTH = 2
        private const val QUERY_DEBOUNCE = 300L
    }

    private val compositeDisposable = CompositeDisposable()

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun clickItem(city: SearchCity) {
        if (city.isSaved) {
            view.showMessageAlreadySaved()
        } else {
            insertCity(city)
        }
    }

    private fun insertCity(searchCity: SearchCity) {
        compositeDisposable += pagedSearchRepository.insert(searchCity)
            .subscribeBy(onSuccess = this::onCityInserted)
    }

    private fun onCityInserted(insertedPosition: Int) {
        view.setInsertResult(insertedPosition)
        view.finishView()
    }

    override fun observeSearchInput(observable: InitialValueObservable<CharSequence>) {
        compositeDisposable += observable
            .filter(this::filterQuery)
            .debounce(QUERY_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map(CharSequence::toString)
            .distinctUntilChanged()
            .switchMap(pagedSearchRepository::loadPage)
            .subscribeBy(onNext = this::onNextQueryList)
    }

    private fun filterQuery(query: CharSequence): Boolean {
        val enough = query.length >= MIN_QUERY_LENGTH
        if (enough) {
            view.showProgressLayout()
        } else {
            view.showEmptyLayout()
        }
        return enough
    }

    private fun onNextQueryList(pagedList: List<SearchCity>) {
        if (pagedList.isNotEmpty()) {
            view.showSearchResult(pagedList, 0)
            view.showListLayout()
        } else {
            view.showNotFoundLayout()
        }
    }
}


