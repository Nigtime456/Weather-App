/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import leakcanary.AppWatcher


/**
 * Base fragment, собирает некоторый код для работы с презентером
 * Подклассы сами присоединяют презентер.
 *
 * listener - activity or parent fragment
 *
 * @param L - parent listener class
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<V : MvpView, P : BasePresenter<V>, L> constructor(@LayoutRes private val layoutRes: Int) :
    Fragment() {

    /**
     * Связанный листенер
     */
    private var previousToast: Toast? = null
    protected var parentListener: L? = null
    protected lateinit var presenter: P

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = getPresenterFactory().createPresenter()

        getListenerClass()?.let { clazz ->
            parentListener = if (clazz.isAssignableFrom(context.javaClass)) {
                clazz.cast(context)
            } else if (parentFragment != null && clazz.isAssignableFrom(requireParentFragment().javaClass)) {
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
        return inflater.inflate(layoutRes, container, false)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    override fun onDetach() {
        super.onDetach()
        AppWatcher.objectWatcher.watch(this, "fragment ${this::class.java}  leak")
        parentListener = null
        previousToast = null
    }


    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        previousToast?.cancel()
        previousToast = Toast.makeText(context, msg, duration)
        previousToast!!.show()

    }

    fun showToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
        val str = requireContext().getString(msg)
        showToast(str, duration)
    }

    /**
     * Вернуть класс listener'a.
     *
     * @return - activity or fragment class,
     * null - without listener
     */
    protected open fun getListenerClass(): Class<L>? = null

    protected abstract fun getPresenterFactory(): PresenterFactory<P>

}