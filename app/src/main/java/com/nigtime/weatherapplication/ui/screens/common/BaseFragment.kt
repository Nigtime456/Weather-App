/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import android.content.Context
import androidx.fragment.app.Fragment
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


/**
 * Base fragment, собирает некоторый код для работы с презентером
 * Подклассы сами присоединяют презентер.
 *
 * @param L - parent listener class
 * @param P - presenter class
 * @param V - MVP view class
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<V : MvpView, P : BasePresenter<V>, L> :
    Fragment() {
    /**
     * Шина для уведомления презентера об событиях ЖЦ
     */
    protected val lifecycleBus: Subject<ExtendLifecycle> = PublishSubject.create()
    /**
     * Презентер
     */
    protected lateinit var presenter: P
    /**
     * Связанный листенер
     */
    protected var listener: L? = null

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = provideMvpPresenter()
        provideListenerClass()?.let { clazz ->
            listener = if (clazz.isAssignableFrom(context.javaClass)) {
                clazz.cast(context)
            } else if (parentFragment != null && clazz.isAssignableFrom(parentFragment!!.javaClass)) {
                clazz.cast(parentFragment)
            } else {
                error("${context.javaClass.name} or $parentFragment must implement ${clazz.name}")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleBus.onNext(ExtendLifecycle.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleBus.onNext(ExtendLifecycle.STOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleBus.onNext(ExtendLifecycle.DESTROY_VIEW)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleBus.onNext(ExtendLifecycle.DESTROY)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycleBus.onNext(ExtendLifecycle.DETACH)
        listener = null
    }

    /**
     * Вернуть листерен.
     * @return - null - without listener
     */
    protected open fun provideListenerClass(): Class<L>? = null

    /**
     * Предоставить презентер
     */
    protected abstract fun provideMvpPresenter(): P

}