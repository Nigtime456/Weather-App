/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.nigtime.weatherapplication.ui.screens.listcities.ListCitiesFragment
import com.nigtime.weatherapplication.ui.screens.pager.CityPagerFragment
import com.nigtime.weatherapplication.ui.screens.searchcity.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment

/**
 * Представляет собой абстракцию для навигации
 * TODO сделать на NavigationFramework
 */
interface Screen {
    fun load(fragmentManager: FragmentManager, @IdRes fragmentContainer: Int)

    companion object {
        val SPLASH = object : Screen {
            override fun load(fragmentManager: FragmentManager, fragmentContainer: Int) {
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, SplashFragment())
                    .commit()
            }
        }

        val SEARCH_CITY = object : Screen {
            override fun load(fragmentManager: FragmentManager, fragmentContainer: Int) {
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, SearchCityFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        val LIST_CITIES = object : Screen {
            override fun load(fragmentManager: FragmentManager, fragmentContainer: Int) {
                fragmentManager.beginTransaction()
                    .replace(
                        fragmentContainer,
                        ListCitiesFragment(),
                        ListCitiesFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        val PAGER = object : Screen {
            override fun load(fragmentManager: FragmentManager, fragmentContainer: Int) {
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, CityPagerFragment())
                    .commit()
            }
        }
    }
}



