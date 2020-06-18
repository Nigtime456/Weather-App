/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.screen.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("MemberVisibilityCanBePrivate")
abstract class RxPresenter : BasePresenter {

    protected val compositeDisposable = CompositeDisposable()

    override fun stop() {
        compositeDisposable.clear()
    }

    protected fun Disposable.disposeOnStop() {
        compositeDisposable.add(this)
    }
}