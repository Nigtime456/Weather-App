/*
 * Сreated by Igor Pokrovsky. 2020/6/18
 */

/*
 * Сreated by Igor Pokrovsky. 2020/6/5
 */

package com.github.nigtime456.weather.trash

import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Данный класс синхронизирует скролл между N страницами, в ViewPager.
 *
 * @param lifecycle - для синхронизации.
 * @param nestedScrollView - [PendingNestedScrollView]
 */
@Suppress("NON_EXHAUSTIVE_WHEN")
class NestedScrollViewSynchronizer(
    private val lifecycle: Lifecycle,
    private val nestedScrollView: PendingNestedScrollView
) : LifecycleEventObserver {

    companion object {
        private val scrollSubject: BehaviorSubject<Int> = BehaviorSubject.create()
    }

    private var disposable: Disposable? = null
    private val nullListener: NestedScrollView.OnScrollChangeListener? = null
    private val scrollListener = NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
        scrollSubject.onNext(scrollY)
    }

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                //фрагмент виден, отписываемся от изменений скролла других страниц
                disposable?.dispose()
                //уведомляем об скролле
                nestedScrollView.setOnScrollChangeListener(scrollListener)
            }

            Lifecycle.Event.ON_START, Lifecycle.Event.ON_PAUSE -> {
                //фрагмент не отображается, удаляем слушатель
                nestedScrollView.setOnScrollChangeListener(nullListener)
                //наблюдаем за изменениями скролла в других страницам
                observeOtherFragScrollChanges()
            }

            Lifecycle.Event.ON_DESTROY -> {
                disposable?.dispose()
            }
        }
    }

    private fun observeOtherFragScrollChanges() {
        disposable = scrollSubject.subscribe { scrollY ->
            nestedScrollView.delayScrollY(scrollY)
        }
    }

}