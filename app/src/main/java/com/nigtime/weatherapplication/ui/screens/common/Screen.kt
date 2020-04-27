/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import android.os.Bundle
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

        fun listCities() = object : Screen {
            override fun load(
                fragmentManager: FragmentManager, @IdRes fragmentContainer: Int,
                args: Bundle?
            ) {

                fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        fragmentContainer,
                        ListCitiesFragment(),
                        ListCitiesFragment::class.java.simpleName
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
                    CityPagerFragment()
                } else {
                    CityPagerFragment.newInstance(page)
                }
                fragmentManager.beginTransaction()
                    .replace(fragmentContainer, frag,"pager")
                    .commit()
            }
        }
    }
}



