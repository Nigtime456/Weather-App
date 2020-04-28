/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nigtime.weatherapplication.ui.screens.pager.PagerCityFragment
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment
import com.nigtime.weatherapplication.ui.screens.wishlist.WishCitiesFragment

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

        fun searchCity(targetFragment: Fragment) = object : Screen {
            override fun load(
                fragmentManager: FragmentManager, @IdRes fragmentContainer: Int,
                args: Bundle?
            ) {
                val frag = SearchCityFragment()
                frag.setTargetFragment(targetFragment, 0)

                fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(fragmentContainer, frag)
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
                    PagerCityFragment()
                } else {
                    PagerCityFragment.newInstance(page)
                }
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, frag, "pager")
                    .commit()
            }
        }
    }
}



