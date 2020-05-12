/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nigtime.weatherapplication.screen.pager.PagerCityFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.splash.SplashFragment
import com.nigtime.weatherapplication.screen.wishlist.WishCitiesFragment

/**
 * Представляет собой абстракцию для навигации
 * TODO сделать на NavigationFramework
 */

interface Screen {
    fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle? = null)

    object Factory {
        private const val WISH_LIST = "wish_list"
        private const val SEARCH = "search"
        private const val PAGER = "pager"
        private const val SPLASH = "splash"


        fun splash() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                manager.beginTransaction()
                    .add(container, SplashFragment::class.java, null)
                    .commit()
            }
        }

        fun searchCity() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val transaction = manager.beginTransaction()
                val currentlyVisible = findCurrentlyVisibleFrag(manager)

                currentlyVisible?.let {
                    transaction.hide(currentlyVisible)
                }
                transaction
                    .add(container, SearchCityFragment::class.java, null)
                    .addToBackStack(SEARCH)
                    .commit()
            }
        }

        fun wishList() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val transaction = manager.beginTransaction()
                val currentlyVisible = findCurrentlyVisibleFrag(manager)

                currentlyVisible?.let {
                    transaction.hide(currentlyVisible)
                }

                transaction
                    .add(container, WishCitiesFragment::class.java, null)
                    .addToBackStack(WISH_LIST)
                    .commit()

            }
        }

        fun pager() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                manager.beginTransaction()
                    .replace(container, PagerCityFragment::class.java, null)
                    .commit()
            }
        }

        fun findCurrentlyVisibleFrag(fragmentManager: FragmentManager): Fragment? {
            return fragmentManager.fragments.last()
        }

    }
}



