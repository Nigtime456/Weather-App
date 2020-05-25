/*
 * Сreated by Igor Pokrovsky. 2020/4/25
 */

package com.gmail.nigtime456.weatherapplication.common.rx

import androidx.recyclerview.widget.DiffUtil
import io.reactivex.Single

/**
 * DiffUtil на потоках Rx
 */
class RxAsyncDiffer constructor(
    private val schedulerProvider: SchedulerProvider
) {
    fun submitList(
        diffCallback: DiffUtil.Callback,
        detectMoves: Boolean = false,
        resultCallback: (DiffUtil.DiffResult) -> Unit
    ) {
        Single.fromCallable { DiffUtil.calculateDiff(diffCallback, detectMoves) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSuccess(resultCallback)
            .ignoreElement()
            .subscribe()
    }
}