/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nigtime.weatherapplication.screen.pager.PagerCityFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.splash.SplashFragment
import com.nigtime.weatherapplication.screen.wishlist.WishCitiesFragment

/**
 * Представляет собой абстракцию для навигации
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
                    .replace(container, manager.findFrag(SPLASH, ::SplashFragment), null)
                    .commit()
            }
        }

        fun searchCity(targetFragment: Fragment? = null) = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val frag = manager.findFrag(SEARCH, ::SearchCityFragment)
                frag.setTargetFragment(targetFragment, 0)

                manager.beginTransaction()
                    .hideCurrentVisible(manager.currentVisibleFrag())
                    .add(container, frag, SEARCH)
                    .addToBackStack(SEARCH)
                    .commit()
            }
        }

        fun wishList(targetFragment: Fragment? = null) = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val frag = manager.findFrag(WISH_LIST, ::WishCitiesFragment)
                frag.setTargetFragment(targetFragment, 0)

                manager.beginTransaction()
                    .hideCurrentVisible(manager.currentVisibleFrag())
                    .add(container, frag, WISH_LIST)
                    .addToBackStack(WISH_LIST)
                    .commit()
            }
        }

        fun pager() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                manager.beginTransaction()
                    .replace(container, manager.findFrag(PAGER, ::PagerCityFragment), PAGER)
                    .commit()
            }
        }

        private fun <T> FragmentManager.findFrag(tag: String, creator: () -> T): T {
            return findFragmentByTag(tag).let { frag -> frag as T } ?: creator()
        }

        private fun FragmentManager.currentVisibleFrag(): Fragment? {
            return fragments.last()
        }

        private fun FragmentTransaction.hideCurrentVisible(fragment: Fragment?): FragmentTransaction {
            fragment?.let { frag -> hide(frag) }
            return this
        }

    }
}



