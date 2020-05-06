/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nigtime.weatherapplication.screens.pager.PagerCityFragment
import com.nigtime.weatherapplication.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.screens.splash.SplashFragment
import com.nigtime.weatherapplication.screens.wishlist.WishCitiesFragment

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
                    .replace(container, SplashFragment())
                    .commit()
            }
        }

        fun searchCity(targetFragment: Fragment) = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val frag = findFrag(manager, SEARCH) { SearchCityFragment() }
                frag.setTargetFragment(targetFragment, 0)

                manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(container, frag, SEARCH)
                    .commit()
            }
        }

        fun wishList() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val frag = findFrag(manager, WISH_LIST) { WishCitiesFragment.newInstance() }

                manager.beginTransaction()
                    .addToBackStack(WISH_LIST)
                    .replace(container, frag, WISH_LIST)
                    .commit()
            }
        }

        fun pager(page: Int = PagerCityFragment.NO_PAGE) = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int, args: Bundle?) {
                val frag = findFrag(manager, PAGER) { PagerCityFragment() }

                frag.setCurrentPager(page)
                manager.beginTransaction()
                    .replace(container, frag, "pager")
                    .commit()
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> findFrag(manager: FragmentManager, tag: String, fallback: () -> T): T {
            return manager.findFragmentByTag(tag).let { frag -> frag as T } ?: fallback()
        }


    }
}



