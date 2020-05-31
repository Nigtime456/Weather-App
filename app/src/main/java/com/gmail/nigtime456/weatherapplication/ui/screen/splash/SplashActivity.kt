/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.splash

import android.app.TaskStackBuilder
import android.os.Bundle
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.CurrentForecastActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.LocationsActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.splash.di.DaggerSplashComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.splash.di.SplashModule
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {
    @Inject
    lateinit var presenter: SplashContract.Presenter

    override fun initDi(appComponent: AppComponent) {
        DaggerSplashComponent.builder()
            .appComponent(appComponent)
            .splashModule(SplashModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        presenter.dispatchScreen()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun showCurrentForecastScreen() {
        val intent = CurrentForecastActivity.getIntent(this)
        startActivity(intent)
    }

    override fun showLocationsScreen() {
        val targetIntent = LocationsActivity.getIntent(this)

        TaskStackBuilder.create(this)
            .addParentStack(LocationsActivity::class.java)
            .addNextIntent(targetIntent)
            .startActivities()
    }
}
