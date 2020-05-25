/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.gmail.nigtime456.weatherapplication.screen.current.pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.common.NavigationController
import com.gmail.nigtime456.weatherapplication.screen.common.Screen
import com.gmail.nigtime456.weatherapplication.screen.current.forecast.CurrentForecastFragment
import com.gmail.nigtime456.weatherapplication.screen.savedlocations.SavedLocationsFragment
import com.gmail.nigtime456.weatherapplication.screen.search.SearchCityFragment
import kotlinx.android.synthetic.main.fragment_location_pages.*


class CurrentForecastPagerFragment :
    BaseFragment<CurrentHostView, CurrentForecastPagerPresenter, NavigationController>(R.layout.fragment_location_pages),
    CurrentHostView, CurrentForecastFragment.ParentListener, SearchCityFragment.TargetFragment,
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

    override fun getPresenterFactory(): BasePresenterProvider<CurrentForecastPagerPresenter> {
        return ViewModelProvider(this).get(CurrentForecastPresenterProvider::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentHostViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
        Log.d("sas", "destroyView")
    }

    private fun initViews() {
        setupNavView()
        setupViewPager()
    }

    private fun setupNavView() {
        currentHostNavView.setNavigationItemSelectedListener { menuItem ->
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
        currentHostDrawer.closeDrawer(GravityCompat.START)
    }

    @SuppressLint("WrongConstant")
    private fun setupViewPager() {
        currentHostViewPager.apply {
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
        attachedListener?.navigateTo(Screen.Factory.dailyForecastHost(location, dayIndex))
    }

    override fun showDrawer() {
        currentHostDrawer.openDrawer(GravityCompat.START)
    }

    override fun submitListToPager(items: List<SavedLocation>) {
        if (currentHostViewPager.adapter == null) {
            currentHostViewPager.adapter = CurrentForecastPageAdapter(this, items)
        } else {
            (currentHostViewPager.adapter as CurrentForecastPageAdapter).submitList(items)
        }
    }

    override fun submitListToNavView(items: List<SavedLocation>) {
        val subMenu = currentHostNavView.menu.findItem(R.id.menuListLocations).subMenu
        subMenu.clear()
        items.forEachIndexed { index, cityForForecast ->
            val menuItem = subMenu.add(0, index, 0, cityForForecast.getName())
            menuItem.setIcon(R.drawable.ic_city)
            menuItem.isCheckable = true
        }
    }

    override fun setCurrentPage(page: Int) {
        currentHostViewPager.setCurrentItem(page, false)
    }

    override fun setCurrentNavItem(index: Int) {
        currentHostNavView.setCheckedItem(index)
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
