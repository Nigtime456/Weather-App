/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.dailypages.DailyPagesFragment
import com.nigtime.weatherapplication.screen.locationpages.LocationPagesFragment
import com.nigtime.weatherapplication.screen.savedlocations.SavedLocationsFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.settings.HostSettingsFragment
import com.nigtime.weatherapplication.screen.splash.SplashFragment

/**
 * Представляет собой абстракцию для навигации
 */
interface Screen {
    fun load(manager: FragmentManager, @IdRes container: Int)

    object Factory {
        private const val SPLASH = "weatherapplication.screen.splash"
        private const val SAVED_LOCATIONS = "weatherapplication.screen.saved_locations"
        private const val SEARCH_CITY = "weatherapplication.screen.search_city"
        private const val LOCATION_PAGES = "weatherapplication.screen.location_pages"
        private const val DAILY_PAGES = "weatherapplication.screen.daily_pages"
        private const val SETTINGS = "weatherapplication.screen.settings"

        fun splash() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                manager.beginTransaction()
                    .replace(container, SplashFragment())
                    .commit()
            }
        }

        fun savedLocations(targetFragment: Fragment? = null) = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                val frag = manager.findFrag(SAVED_LOCATIONS, ::SavedLocationsFragment)
                frag.setTargetFragment(targetFragment, 0)

                manager.beginTransaction()
                    .hideCurrentVisible(manager.currentVisibleFrag())
                    .add(container, frag, SAVED_LOCATIONS)
                    .addToBackStack(SAVED_LOCATIONS)
                    .commit()
            }
        }

        fun searchCity(targetFragment: Fragment? = null) = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                val frag = manager.findFrag(SEARCH_CITY, ::SearchCityFragment)
                frag.setTargetFragment(targetFragment, 0)

                manager.beginTransaction()
                    .hideCurrentVisible(manager.currentVisibleFrag())
                    .add(container, frag, SEARCH_CITY)
                    .addToBackStack(SEARCH_CITY)
                    .commit()
            }
        }

        fun locationPages() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                manager.beginTransaction()
                    .replace(
                        container,
                        manager.findFrag(LOCATION_PAGES, ::LocationPagesFragment),
                        LOCATION_PAGES
                    )
                    .commit()
            }
        }

        fun dailyPages(location: SavedLocation, dayIndex: Int) = object : Screen {
            override fun load(manager: FragmentManager, container: Int) {
                val frag = DailyPagesFragment.newInstance(location, dayIndex)
                manager.beginTransaction()
                    .add(container, frag)
                    .addToBackStack(DAILY_PAGES)
                    .commit()
            }
        }

        fun settings() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                manager.beginTransaction()
                    .add(container, manager.findFrag(SETTINGS, ::HostSettingsFragment))
                    .addToBackStack(SETTINGS)
                    .commit()
            }
        }


        @Suppress("UNCHECKED_CAST")
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



