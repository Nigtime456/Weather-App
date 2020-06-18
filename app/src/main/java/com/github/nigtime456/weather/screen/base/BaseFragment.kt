/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

package com.github.nigtime456.weather.screen.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.nigtime456.weather.App
import com.github.nigtime456.weather.di.AppComponent

abstract class BaseFragment<L> : Fragment() {
    protected var listener: L? = null
    private var hasStop = false

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        getListenerClass()?.let { clazz ->
            listener = if (clazz.isAssignableFrom(context.javaClass)) {
                clazz.cast(context)
            } else if (parentFragment != null && clazz.isAssignableFrom(requireParentFragment().javaClass)) {
                clazz.cast(parentFragment)
            } else {
                error("Context = [${context.javaClass.name}] or ParenFragment [$parentFragment] must implement ${clazz.name}")
            }
        }

        initDi(App.getComponent())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val resId = getLayoutResId()
        return if (resId != 0) {
            inflater.inflate(resId, container, false)
        } else {
            null
        }
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
        listener = null
    }

    /**
     *
     * @return - activity or fragment class,
     * null - without listener
     */
    protected open fun getListenerClass(): Class<L>? = null

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun initDi(appComponent: AppComponent)
}