/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.common

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.current.pager.CurrentForecastPagerFragment
import com.gmail.nigtime456.weatherapplication.screen.daily.host.DailyHostFragment
import com.gmail.nigtime456.weatherapplication.screen.notifications.NotificationsFragment
import com.gmail.nigtime456.weatherapplication.screen.savedlocations.SavedLocationsFragment
import com.gmail.nigtime456.weatherapplication.screen.search.SearchCityFragment
import com.gmail.nigtime456.weatherapplication.screen.settings.HostSettingsFragment
import com.gmail.nigtime456.weatherapplication.screen.splash.SplashFragment

/**
 * Представляет собой абстракцию для навигации
 */
//TODO почистить код
interface Screen {
    fun load(manager: FragmentManager, @IdRes container: Int)

    object Factory {
        private const val SPLASH = "weatherapplication.screen.splash"
        private const val SAVED_LOCATIONS = "weatherapplication.screen.saved_locations"
        private const val SEARCH_CITY = "weatherapplication.screen.search_city"
        private const val CURRENT_HOST = "weatherapplication.screen.current_host"
        private const val DAILY_HOST = "weatherapplication.screen.daily_host"
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
                    //  .hideCurrentVisible(manager.currentVisibleFrag())
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
                    //  .hideCurrentVisible(manager.currentVisibleFrag())
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left)
                    .replace(container, frag, SEARCH_CITY)
                    .addToBackStack(SEARCH_CITY)
                    .commit()
            }
        }

        fun currentForecastHost() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left)
                    .replace(
                        container,
                        manager.findFrag(CURRENT_HOST, ::CurrentForecastPagerFragment),
                        CURRENT_HOST
                    )
                    .commit()
            }
        }

        fun dailyForecastHost(location: SavedLocation, dayIndex: Int) = object : Screen {
            override fun load(manager: FragmentManager, container: Int) {
                val frag = DailyHostFragment.newInstance(location, dayIndex)
                manager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right
                    )
                    .add(container, frag)
                    .addToBackStack(DAILY_HOST)
                    .commit()
            }
        }

        fun notifications() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                manager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right
                    )
                    .add(container, NotificationsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun settings() = object : Screen {
            override fun load(manager: FragmentManager, @IdRes container: Int) {
                manager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right
                    )
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



