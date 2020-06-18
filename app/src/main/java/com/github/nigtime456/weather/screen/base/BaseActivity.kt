/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.screen.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.github.nigtime456.weather.App
import com.github.nigtime456.weather.di.AppComponent

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDi(App.getComponent())
    }

    protected fun getTransitionOptions(): Bundle? {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this)
            .toBundle()
    }

    protected open fun initDi(appComponent: AppComponent) {

    }
}