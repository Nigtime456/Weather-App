/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.animation.BackgroundColorProperty
import com.nigtime.weatherapplication.common.helper.ThemeHelper
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterFactory
import com.nigtime.weatherapplication.screen.common.Screen
import kotlinx.android.synthetic.main.fragment_splash.*

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

    override fun startSplashAnimation() {
        val backgroundAnimator = getBackgroundAnimator()
        val rotateAnimator = getRotateAnimator()
        AnimatorSet().apply {
            duration = resources.getInteger(R.integer.splash_rotate_color_transition).toLong()
            play(backgroundAnimator).with(rotateAnimator)
            start()
        }

    }

    override fun navigateToPagerScreen() {
        parentListener?.navigateTo(Screen.Factory.pager())
    }

    override fun navigateToWishListScreen() {
        parentListener?.navigateTo(Screen.Factory.wishList())
    }

    private fun getRotateAnimator(): Animator {
        return ObjectAnimator.ofFloat(splashIco, View.ROTATION, 0f, 360f).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
        }
    }

    private fun getBackgroundAnimator(): ObjectAnimator {
        val startColor = ThemeHelper.getColor(requireContext(), R.attr.themeNightColor)
        val endColor = ThemeHelper.getColor(requireContext(), R.attr.themeDayColor)
        return ObjectAnimator.ofArgb(
            splashRoot,
            BackgroundColorProperty(),
            startColor,
            endColor
        )
    }


}
