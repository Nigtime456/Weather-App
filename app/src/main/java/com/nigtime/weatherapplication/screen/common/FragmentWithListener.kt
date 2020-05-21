/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.screen.common

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import leakcanary.AppWatcher

abstract class FragmentWithListener<L>(@LayoutRes private val layoutRes: Int) :
    Fragment(layoutRes) {
    protected var parentListener: L? = null

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
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this, "fragment ${this::class.java}  leak")
    }

    override fun onDetach() {
        super.onDetach()
        parentListener = null
    }

    /**
     * Вернуть класс listener'a.
     *
     * @return - activity or fragment class,
     * null - without listener
     */
    protected open fun getListenerClass(): Class<L>? = null

}