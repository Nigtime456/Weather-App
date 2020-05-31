/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.splash


import com.gmail.nigtime456.weatherapplication.domain.repository.LocationsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val view: SplashContract.View,
    private val locationsRepository: LocationsRepository
) : SplashContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun dispatchScreen() {
        compositeDisposable += locationsRepository.hasLocations()
            .subscribeBy { hasLocations ->
                if (hasLocations) {
                    preloadLocations()
                } else {
                    view.showLocationsScreen()
                }
            }
    }

    /**
     * Загрузить локации в первый раз, репозиторий закэширует их
     */
    private fun preloadLocations() {
        compositeDisposable += locationsRepository.getLocations()
            .subscribe {
                view.showCurrentForecastScreen()
            }
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}