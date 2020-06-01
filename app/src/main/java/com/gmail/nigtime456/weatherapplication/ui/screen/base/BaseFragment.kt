/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.gmail.nigtime456.weatherapplication.App
import com.gmail.nigtime456.weatherapplication.di.AppComponent

abstract class BaseFragment<L>(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    protected var parentListener: L? = null
    private var hasStop = false

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        getListenerClass()?.let { clazz ->
            parentListener = if (clazz.isAssignableFrom(context.javaClass)) {
                clazz.cast(context)
            } else if (parentFragment != null && clazz.isAssignableFrom(requireParentFragment().javaClass)) {
                clazz.cast(parentFragment)
            } else {
                error("Context = [${context.javaClass.name}] or ParenFragment [$parentFragment] must implement ${clazz.name}")
            }
        }

        initDi(App.getComponent())
    }

    override fun onStart() {
        super.onStart()
        if (hasStop) {
            hasStop = false
            onRestart()
        }
    }

    protected open fun onRestart() {

    }

    override fun onStop() {
        super.onStop()
        hasStop = true
    }

    override fun onDetach() {
        super.onDetach()
        parentListener = null
    }

    protected fun requireListener() = parentListener ?: error("listener is null")

    /**
     * Вернуть класс listener'a.
     *
     * @return - activity or fragment class,
     * null - without listener
     */
    protected open fun getListenerClass(): Class<L>? = null


    protected abstract fun initDi(appComponent: AppComponent)

}