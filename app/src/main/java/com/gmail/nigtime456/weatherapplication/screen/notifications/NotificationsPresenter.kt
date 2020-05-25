/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.notifications

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocationsRepository
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter
import io.reactivex.rxkotlin.subscribeBy

class NotificationsPresenter(private val savedLocationsRepository: SavedLocationsRepository) :
    BasePresenter<NotificationsView>(TAG) {
    companion object {
        const val TAG = "notification"
    }

    override fun onAttach() {
        super.onAttach()
        provideLocations()
    }

    private fun provideLocations() {
        savedLocationsRepository.getLocationsOnce()
            .doOnSubscribe { getView()?.showProgressLayout() }
            .subscribeBy(onSuccess = this::onListLoaded)
            .disposeOnDestroy()
    }

    private fun onListLoaded(list: List<SavedLocation>) {
        getView()?.submitList(list)
    }


}