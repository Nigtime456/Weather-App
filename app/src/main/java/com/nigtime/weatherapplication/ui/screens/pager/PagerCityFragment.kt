/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.ui.screens.pager

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.di.CitiesRepositoryFactory
import com.nigtime.weatherapplication.common.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import kotlinx.android.synthetic.main.fragment_pager.*


class PagerCityFragment : BaseFragment<PagerCityFragment.ActivityListener>(R.layout.fragment_pager),
    PagerCityView, CurrentForecastFragment.ParentListener, SearchCityFragment.TargetFragment {

    interface ActivityListener : NavigationController

    companion object {
        private const val EXTRA_PAGE = "com.nigtime.weatherapp.city_pager.page"

        fun newInstance(page: Int): PagerCityFragment {
            return PagerCityFragment().apply {
                arguments = bundleOf(EXTRA_PAGE to page)
            }
        }
    }

    private lateinit var presenter: PagerCityPresenter
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = PagerCityPresenter(
            MainSchedulerProvider.INSTANCE,
            CitiesRepositoryFactory.getForecastCitiesRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.handlePagerPosition(arguments?.getInt(EXTRA_PAGE) ?: 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureViews()
        presenter.provideCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
    }

    override fun onStop() {
        super.onStop()
        presenter.handlePagerPosition(pagerViewPager.currentItem)
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
