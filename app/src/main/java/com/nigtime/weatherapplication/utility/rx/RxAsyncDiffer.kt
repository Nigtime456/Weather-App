/*
 * Сreated by Igor Pokrovsky. 2020/4/25
 */

package com.nigtime.weatherapplication.utility.rx

import androidx.recyclerview.widget.DiffUtil
import io.reactivex.Single

class RxAsyncDiffer constructor(
    private val schedulerProvider: MainSchedulerProvider
) {
    fun submitList(
        callback: DiffUtil.Callback,
        detectMoves: Boolean = true,
        finishCallback: (DiffUtil.DiffResult) -> Unit
    ) {
        Single.fromCallable { DiffUtil.calculateDiff(callback, detectMoves) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSuccess(finishCallback)
            .ignoreElement()
            .subscribe()
    }
}