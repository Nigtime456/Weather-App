/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


/**
 * Base fragment, собирает некоторый код для работы с презентером
 * Подклассы сами присоединяют презентер.
 *
 * @param L - parent listener class
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<L> constructor(
    @LayoutRes private val layoutRes: Int
) :
    Fragment() {
    /**
     * Шина для уведомления презентера об событиях ЖЦ
     */
    protected val lifecycleBus: Subject<ExtendLifecycle> = PublishSubject.create()
    /**
     * Связанный листенер
     */
    protected var parentListener: L? = null

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        provideListenerClass()?.let { clazz ->
            parentListener = if (clazz.isAssignableFrom(context.javaClass)) {
                clazz.cast(context)
            } else if (parentFragment != null && clazz.isAssignableFrom(parentFragment!!.javaClass)) {
                clazz.cast(parentFragment)
            } else {
                error("${context.javaClass.name} or $parentFragment must implement ${clazz.name}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(layoutRes, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycleBus.onNext(ExtendLifecycle.DETACH)
        parentListener = null

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


    /**
     * Вернуть класс listener'a.
     * @return - null - without listener
     */
    protected open fun provideListenerClass(): Class<L>? = null

}