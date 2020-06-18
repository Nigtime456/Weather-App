/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.github.nigtime456.weather.screen.splash

import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Bundle
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.base.BaseActivity
import com.github.nigtime456.weather.screen.currently.activity.CurrentlyActivity
import com.github.nigtime456.weather.screen.locations.LocationsActivity
import com.github.nigtime456.weather.screen.splash.di.DaggerSplashComponent
import com.github.nigtime456.weather.screen.splash.di.SplashModule
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: SplashContract.Presenter

    //</editor-fold>

    //<editor-fold desc = "lifecycle">

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

    //</editor-fold>

    //<editor-fold desc = "initialization">

    override fun initDi(appComponent: AppComponent) {
        DaggerSplashComponent.builder()
            .appComponent(appComponent)
            .splashModule(SplashModule(this))
            .build()
            .inject(this)
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun showCurrentForecastScreen() {
        val intent = CurrentlyActivity.getIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        //TODO transition
        startActivity(intent)
    }

    override fun showLocationsScreen() {
        val targetIntent = LocationsActivity.getIntent(this)

        //TODO transition
        TaskStackBuilder.create(this)
            .addParentStack(LocationsActivity::class.java)
            .addNextIntent(targetIntent)
            .startActivities()
    }
    //</editor-fold>
}
