/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.screen.splash_t

import android.os.Bundle
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.screen.common.BaseActivity

class SplashActivity : BaseActivity(), SplashContract.View {
    override var presenter: SplashContract.Presenter = presenterFactory.splashPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun navigateToCurrentForecastScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToSavedLocationsScreen() {
        TODO("Not yet implemented")
    }

}
