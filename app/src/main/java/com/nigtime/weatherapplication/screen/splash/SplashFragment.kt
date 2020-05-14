/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterFactory
import com.nigtime.weatherapplication.screen.common.Screen

/**
 * TODO поправь сплэш
 * Сплэш должен отображать анимацию при старте и показать её бесконечно, пока
 * не будет выполнена инициализация. (инициализация может продлиться долго)
 * Так же надо предусмотреть минимальный тайминг отображения.
 */
class SplashFragment :
    BaseFragment<SplashView, WrongSplashPresenter, NavigationController>(R.layout.fragment_splash),
    SplashView {

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterFactory(): PresenterFactory<WrongSplashPresenter> {
        return ViewModelProvider(this).get(SplashPresenterFactory::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkReferenceCities()
    }

    override fun finishSplash() {
        //удаляем фон
        activity?.window?.setBackgroundDrawable(null)
    }


    override fun navigateToPagerScreen() {
        parentListener?.navigateTo(Screen.Factory.pager())
    }

    override fun navigateToWishListScreen() {
        parentListener?.navigateTo(Screen.Factory.wishList())
    }

}
