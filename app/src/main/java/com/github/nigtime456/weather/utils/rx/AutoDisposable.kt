/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.utils.rx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposable(
    lifecycle: Lifecycle,
    private val disposeOn: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) {
    private val compositeDisposable = CompositeDisposable()

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == disposeOn) {
                    source.lifecycle.removeObserver(this)
                    compositeDisposable.clear()
                }
            }
        })
    }

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}