/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.locations

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.LocationsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class LocationsPresenter @Inject constructor(
    private val view: LocationsContract.View,
    private val locationsRepository: LocationsRepository
) : LocationsContract.Presenter {

    private companion object {
        const val TAG = "locations"
        const val NO_POSITION = -1
    }

    private val compositeDisposable = CompositeDisposable()
    private var insertedPosition = NO_POSITION
    private var locationsCount = 0

    override fun stop() {
        insertedPosition = NO_POSITION
        compositeDisposable.clear()
    }

    override fun loadLocations() {
        compositeDisposable += locationsRepository.getLocations()
            .take(1)
            .doOnSubscribe { view.showProgressLayout() }
            .subscribeBy { list ->
                Timber.tag(TAG).d("submit list = $list")
                locationsCount = list.size
                if (list.isNotEmpty()) {
                    view.showLocations(list)
                    view.showListLayout()
                } else {
                    view.showEmptyLayout()
                }
            }
    }

    override fun removeItem(item: SavedLocation) {
        Timber.tag(TAG).d("delete = $item")

        locationsRepository.delete(item).subscribe()
        locationsCount--
        view.showMessageRemoved()
    }

    override fun updateItems(items: List<SavedLocation>) {
        Timber.tag(TAG).d("update = $items")

        locationsRepository.update(items)
            .subscribe()
    }

    override fun onListUpdated() {
        if (insertedPosition != NO_POSITION) {
            view.scrollTo(insertedPosition)
        }
    }

    override fun clickItem(position: Int) {
        view.setSelectResult(position)
        view.finishView()
    }

    override fun clickAddCity() {
        view.showSearchScreen()
    }

    override fun backPressed() {
        if (locationsCount == 0) {
            view.showDialogEmptyList()
        } else {
            view.finishView()
        }
    }

    override fun onCityInserted(position: Int) {
        insertedPosition = position
    }

}