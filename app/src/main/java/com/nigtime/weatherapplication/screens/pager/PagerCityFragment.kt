/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.screens.pager

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screens.common.BaseFragment
import com.nigtime.weatherapplication.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.screens.common.NavigationController
import com.nigtime.weatherapplication.screens.common.Screen
import com.nigtime.weatherapplication.screens.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.screens.search.SearchCityFragment
import kotlinx.android.synthetic.main.fragment_pager.*


class PagerCityFragment : BaseFragment<PagerCityFragment.ActivityListener>(R.layout.fragment_pager),
    PagerCityView, CurrentForecastFragment.ParentListener, SearchCityFragment.TargetFragment {

    interface ActivityListener : NavigationController

    companion object {
        //TODO constant
        private const val EXTRA_PAGE = "com.nigtime.weatherapp.city_pager.page"
        const val NO_PAGE = -1

    }

    private var presenter: PagerCityPresenter =
        App.INSTANCE.appContainer.pagerCityContainer.pagerCityPresenter
    private lateinit var pagerCityAdapter: PagerCityAdapter

    private val pagerScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            setNavigationItem(position)
        }
    }

    override fun provideListenerClass(): Class<ActivityListener>? = ActivityListener::class.java

    fun setCurrentPager(page: Int) {
        if (page != NO_PAGE) {
            presenter.handlePagerPosition(page)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureViews()
        presenter.provideCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.handlePagerPosition(pagerViewPager.currentItem)
        pagerViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
    }


    private fun configureViews() {
        pagerNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutApp -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                R.id.menuChangeCityList -> {
                    parentListener?.navigateTo(Screen.Factory.wishList())
                }
                R.id.menuSettings -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                else -> {
                    //TODO костыль
                    setCurrentPage(menuItem.itemId - 1000, true)
                }
            }
            closeDrawer()
            true
        }
        pagerCityAdapter = PagerCityAdapter(this)
        configureViewPager()
    }


    private fun configureViewPager() {
        pagerViewPager.apply {
            adapter = pagerCityAdapter
            registerOnPageChangeCallback(pagerScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    private fun setCurrentPage(page: Int, smoothScroll: Boolean) {
        pagerViewPager.setCurrentItem(page, smoothScroll)
    }

    private fun closeDrawer() {
        pagerDrawer.closeDrawer(GravityCompat.START)
    }

    private fun openDrawer() {
        pagerDrawer.openDrawer(GravityCompat.START)
    }

    override fun submitList(items: List<CityForForecast>) {
        pagerCityAdapter.submitList(items)
        buildDrawerList(items)
    }

    private fun buildDrawerList(items: List<CityForForecast>) {
        items.forEachIndexed { index: Int, city: CityForForecast ->
            val menuItem = pagerNavView.menu
                .getItem(0)
                .subMenu
                .add(0, getNavViewItemId(index), 0, city.cityName)
            menuItem.isCheckable = true
            menuItem.setIcon(R.drawable.ic_location_city)
        }
    }

    private fun getNavViewItemId(position: Int): Int {
        //TODO костыль
        return position + 1000
    }

    override fun setPage(page: Int) {
        setCurrentPage(page, false)
        setNavigationItem(page)
    }

    private fun setNavigationItem(index: Int) {
        pagerNavView.setCheckedItem(getNavViewItemId(index))
    }


    override fun onClickAddCity() {
        parentListener?.navigateTo(Screen.Factory.searchCity(this))
    }


    override fun onClickOpenDrawer() {
        openDrawer()
    }

    override fun onCityInserted(position: Int) {
        presenter.handlePagerPosition(position)
    }
}
