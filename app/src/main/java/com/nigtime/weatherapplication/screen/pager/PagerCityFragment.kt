/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.screen.pager

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
    BaseFragment<PagerCityView, PagerCityPresenter, PagerCityFragment.ParentListener>(R.layout.fragment_pager),
    PagerCityView, CurrentForecastFragment.ParentListener, SearchCityFragment.TargetFragment,
    WishCitiesFragment.TargetFragment {

    interface ParentListener : NavigationController


    private val pagerScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            presenter.onPageScrolled(position)
        }

    }

    override fun provideListenerClass(): Class<ParentListener>? = ParentListener::class.java

    override fun getPresenterHolder(): PresenterProvider<PagerCityPresenter> {
        return ViewModelProvider(this).get(PagerCityViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        presenter.provideCities()
    }

    override fun onStop() {
        super.onStop()
        presenter.setPagerPosition(pagerViewPager.currentItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
        pagerViewPager.adapter = null
        pagerViewPager.removeAllViews()
        pagerNavView.removeAllViews()
    }


    private fun configureViews() {
        pagerNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutApp -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                R.id.menuChangeCityList -> {
                    presenter.onClickChangeCityList()
                }
                R.id.menuSettings -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                else -> {
                    presenter.onClickNavigationItem(menuItem.itemId)
                }
            }
            closeDrawer()
            true
        }
        configureViewPager()
    }


    private fun configureViewPager() {
        pagerViewPager.apply {
            adapter = PagerCityAdapter(childFragmentManager, lifecycle)
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

    override fun submitPageList(items: List<CityForForecast>) {
        (pagerViewPager.adapter as PagerCityAdapter).submitList(items)
    }

    override fun submitNavigationList(items: List<Pair<Int, String>>) {
        items.forEach { pair ->
            val menuItem = pagerNavView.menu
                .getItem(0)
                .subMenu
                .add(0, pair.first, 0, pair.second)
            menuItem.isCheckable = true
            menuItem.setIcon(R.drawable.ic_location_city)
        }
    }

    override fun setPage(page: Int, smoothScroll: Boolean) {
        pagerViewPager.setCurrentItem(page, smoothScroll)
    }

    override fun navigateToWishListScreen() {
        parentListener?.navigateTo(Screen.Factory.wishList(this))
    }


    override fun setNavigationItem(index: Int) {
        pagerNavView.setCheckedItem(index)
    }

    override fun onClickAddCity() {
        parentListener?.navigateTo(Screen.Factory.searchCity(this))
    }

    override fun onClickOpenDrawer() {
        openDrawer()
    }

    override fun onCityInserted(position: Int) {
        presenter.setPagerPosition(position)
    }

    override fun onSelectCity(position: Int) {
        presenter.setPagerPosition(position)
    }
}
