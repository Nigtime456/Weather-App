/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.nigtime.weatherapplication.ui.screens.wishlist.WishCitiesFragment
import com.nigtime.weatherapplication.ui.screens.pager.PagerFragmentCity
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment

/**
 * Представляет собой абстракцию для навигации
 * TODO сделать на NavigationFramework
 */
interface Screen {
    fun load(fragmentManager: FragmentManager, @IdRes fragmentContainer: Int, args: Bundle? = null)

    object Factory {

        fun splash() = object : Screen {
            override fun load(
                fragmentManager: FragmentManager, @IdRes fragmentContainer: Int,
                args: Bundle?
            ) {
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, SplashFragment())
                    .commit()
            }
        }

        fun searchCity() = object : Screen {
            override fun load(
                fragmentManager: FragmentManager, @IdRes fragmentContainer: Int,
                args: Bundle?
            ) {
                fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(fragmentContainer, SearchCityFragment())
                    .commit()
            }
        }

        fun wishList() = object : Screen {
            override fun load(
                fragmentManager: FragmentManager, @IdRes fragmentContainer: Int,
                args: Bundle?
            ) {

                fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        fragmentContainer,
                        WishCitiesFragment(),
                        WishCitiesFragment::class.java.simpleName
                    )
                    .commit()
            }
        }

        fun pager(page: Int = -1) = object : Screen {
            override fun load(
                fragmentManager: FragmentManager, @IdRes fragmentContainer: Int,
                args: Bundle?
            ) {
                val frag = if (page == -1) {
                    PagerFragmentCity()
                } else {
                    PagerFragmentCity.newInstance(page)
                }
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, frag,"pager")
                    .commit()
            }
        }
    }
}



