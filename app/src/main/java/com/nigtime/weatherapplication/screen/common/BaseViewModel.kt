/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.nigtime.weatherapplication.screen.common

import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import leakcanary.AppWatcher

abstract class BaseViewModel : ViewModel() {
    protected val appContainer = App.INSTANCE.appContainer


    override fun onCleared() {
        super.onCleared()
        AppWatcher.objectWatcher.watch(this,"ViewModel should be cleared")
    }
}