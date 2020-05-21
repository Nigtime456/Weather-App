/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes


/**
 * Базовый фрагмент, собирает некоторый код для работы с презентером
 * Подклассы сами присоединяют презентер.
 *
 * listener - activity or parent fragment
 *
 * @param L - parent listener class
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<V, P : BasePresenter<V>, L>(@LayoutRes private val layoutRes: Int) :
    FragmentWithListener<L>(layoutRes) {

    /**
     * Связанный листенер
     */
    private var previousToast: Toast? = null
    protected lateinit var presenter: P


    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = getPresenterProvider().getPresenter()
    }

    override fun onDetach() {
        super.onDetach()
        previousToast = null
    }

    @Suppress("UNCHECKED_CAST")
    override fun onStart() {
        super.onStart()
        val isNotHidden = !isHidden
        if (isNotHidden) {
            presenter.attach(this as V)
        }
    }

    override fun onStop() {
        super.onStop()
        if (presenter.isViewAttached())
            presenter.detach()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            presenter.detach()
        } else {
            presenter.attach(this as V)
        }
    }


    fun showToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
        previousToast?.cancel()
        previousToast = Toast.makeText(context, msg, duration)
        previousToast?.show()
    }

    protected abstract fun getPresenterProvider(): BasePresenterProvider<P>
}