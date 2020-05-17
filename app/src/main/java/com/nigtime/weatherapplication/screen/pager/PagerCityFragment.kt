/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.screen.pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import com.nigtime.weatherapplication.screen.common.Screen
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.wishlist.WishCitiesFragment
import kotlinx.android.synthetic.main.fragment_pager.*


class PagerCityFragment :
    BaseFragment<PagerCityView, PagerCityPresenter, NavigationController>(R.layout.fragment_pager),
    PagerCityView, CurrentForecastFragment.ParentListener, SearchCityFragment.TargetFragment,
    WishCitiesFragment.TargetFragment {

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

    override fun getPresenterProvider(): PresenterProvider<PagerCityPresenter> {
        return ViewModelProvider(this).get(PagerCityPresenterProvider::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
    }

    private fun initViews() {
        setupNavView()
        setupViewPager()
    }

    private fun setupNavView() {
        pagerNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutApp -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                R.id.menuChangeCityList -> {
                    presenter.onChangeCityListClick()
                }
                R.id.menuSettings -> {
                    presenter.onSettingsClick()
                }
                else -> {
                    presenter.onNavigationItemClick(menuItem.itemId)
                }
            }
            closeDrawer()
            true
        }
    }

    @SuppressLint("WrongConstant")
    private fun setupViewPager() {
        pagerViewPager.apply {
            offscreenPageLimit = PAGE_LIMIT
            registerOnPageChangeCallback(pagerScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    private fun closeDrawer() {
        pagerDrawer.closeDrawer(GravityCompat.START)
    }

    private fun openDrawer() {
        pagerDrawer.openDrawer(GravityCompat.START)
    }

    override fun submitListToPager(items: List<CityForForecast>) {
        if (pagerViewPager.adapter == null) {
            pagerViewPager.adapter = PagerCityAdapter(this, items)
        } else {
            (pagerViewPager.adapter as PagerCityAdapter).submitList(items)
        }
    }

    override fun submitListToNavView(items: List<CityForForecast>) {
        val subMenu = pagerNavView.menu.findItem(R.id.menuListCities).subMenu
        subMenu.clear()
        items.forEachIndexed { index, cityForForecast ->
            val menuItem = subMenu.add(0, index, 0, cityForForecast.cityName)
            menuItem.setIcon(R.drawable.ic_location_city)
            menuItem.isCheckable = true
        }
    }

    override fun setCurrentPage(page: Int) {
        pagerViewPager.setCurrentItem(page, false)
    }

    override fun setCurrentNavItem(index: Int) {
        pagerNavView.setCheckedItem(index)
    }

    override fun onClickAddCity() {
        presenter.onAddCityClick()
    }

    override fun navigateToWishListScreen() {
        parentListener?.navigateTo(Screen.Factory.wishList(this))
    }

    override fun navigateToSearchCityScreen() {
        parentListener?.navigateTo(Screen.Factory.searchCity(this))
    }

    override fun navigateToSettingsScreen() {
        parentListener?.navigateTo(Screen.Factory.settings())
    }

    override fun onClickOpenDrawer() {
        openDrawer()
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInserted(position)
    }

    override fun onCitySelected(position: Int) {
        presenter.onCitySelected(position)
    }
}
