/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.common

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.common.App
import com.gmail.nigtime456.weatherapplication.common.mvp.BasePresenter
import com.gmail.nigtime456.weatherapplication.common.mvp.PresenterFactory


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

    private val presenterProvider = App.INSTANCE.presenterProvider
    private var previousToast: Toast? = null
    protected lateinit var presenter: P

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = presenterProvider.getPresenter(javaClass.name, getPresenterFactory())
    }

    override fun onDetach() {
        super.onDetach()
        previousToast = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this as V)
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

    protected abstract fun getPresenterFactory(): PresenterFactory<P>
}