/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.screen.locationpages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.screen.savedlocations.SavedLocationsFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import kotlinx.android.synthetic.main.fragment_location_pages.*


class LocationPagesFragment :
    BaseFragment<LocationPagesView, LocationPagesPresenter, NavigationController>(R.layout.fragment_location_pages),
    LocationPagesView, CurrentForecastFragment.ParentListener, SearchCityFragment.TargetFragment,
    SavedLocationsFragment.TargetFragment {


    companion object {
        private const val PAGE_LIMIT = 1
    }

    private val pagerScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            presenter.onPageScrolled(position)
        }
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): BasePresenterProvider<LocationPagesPresenter> {
        return ViewModelProvider(this).get(LocationPagesPresenterProvider::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        locationPagesViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
    }

    private fun initViews() {
        setupNavView()
        setupViewPager()
    }

    private fun setupNavView() {
        locationPagesNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutApp -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                R.id.menuChangeListLocations -> {
                    presenter.onChangeListLocationsClick()
                }
                R.id.menuSettings -> {
                    presenter.onSettingsClick()
                }
                R.id.menuWeatherNotifications -> {
                    attachedListener?.navigateTo(Screen.Factory.notifications())
                }
                else -> {
                    presenter.onNavigationItemClick(menuItem.itemId)
                }
            }
            closeDrawer()
            true
        }
    }

    private fun closeDrawer() {
        locationPagesDrawer.closeDrawer(GravityCompat.START)
    }

    @SuppressLint("WrongConstant")
    private fun setupViewPager() {
        locationPagesViewPager.apply {
            offscreenPageLimit = PAGE_LIMIT
            registerOnPageChangeCallback(pagerScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    override fun onAddCityClick() {
        presenter.onAddCityClick()
    }

    override fun onOpenDrawerClick() {
        presenter.onOpenDrawerClick()
    }

    override fun navigateToDailyPagesFragment(location: SavedLocation, dayIndex: Int) {
        attachedListener?.navigateTo(Screen.Factory.dailyPages(location, dayIndex))
    }

    override fun showDrawer() {
        locationPagesDrawer.openDrawer(GravityCompat.START)
    }

    override fun submitListToPager(items: List<SavedLocation>) {
        if (locationPagesViewPager.adapter == null) {
            locationPagesViewPager.adapter = LocationPagesAdapter(this, items)
        } else {
            (locationPagesViewPager.adapter as LocationPagesAdapter).submitList(items)
        }
    }

    override fun submitListToNavView(items: List<SavedLocation>) {
        val subMenu = locationPagesNavView.menu.findItem(R.id.menuListLocations).subMenu
        subMenu.clear()
        items.forEachIndexed { index, cityForForecast ->
            val menuItem = subMenu.add(0, index, 0, cityForForecast.getName())
            menuItem.setIcon(R.drawable.ic_city)
            menuItem.isCheckable = true
        }
    }

    override fun setCurrentPage(page: Int) {
        locationPagesViewPager.setCurrentItem(page, false)
    }

    override fun setCurrentNavItem(index: Int) {
        locationPagesNavView.setCheckedItem(index)
    }

    override fun navigateToSavedLocationsScreen() {
        attachedListener?.navigateTo(Screen.Factory.savedLocations(this))
    }

    override fun navigateToSearchCityScreen() {
        attachedListener?.navigateTo(Screen.Factory.searchCity(this))
    }

    override fun navigateToSettingsScreen() {
        attachedListener?.navigateTo(Screen.Factory.settings())
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInserted(position)
    }

    override fun onLocationSelected(position: Int) {
        presenter.onLocationSelected(position)
    }
}
