/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.ui.screens.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.CityForForecastData
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.db.source.SelectedCitySourceImpl
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.ui.tools.ThemeHelper
import com.nigtime.weatherapplication.ui.tools.list.ColorDividerDecoration
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import kotlinx.android.synthetic.main.fragment_city_pager.*
import kotlinx.android.synthetic.main.fragment_city_pager_drawer.*


class CityPagerFragment :
    BaseFragment<CityPagerView, CityPagerPresenter, CityPagerFragment.ActivityListener>(),
    CityPagerView,
    CurrentForecastFragment.ParentListener {

    interface ActivityListener : NavigationController

    companion object {
        private const val EXTRA_PAGE = "com.nigtime.weatherapp.city_pager.page"

        fun newInstance(page: Int): CityPagerFragment {
            return CityPagerFragment().apply {
                arguments = bundleOf(EXTRA_PAGE to page)
            }
        }
    }

    private var currentPage = 0
    private val pagerScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            (fragmentCityPagerDrawerList.adapter as DrawerListAdapter).setActivatedItem(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPage = arguments?.getInt(EXTRA_PAGE) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_pager, container, false)
    }

    override fun provideListenerClass(): Class<ActivityListener>? = ActivityListener::class.java

    override fun provideMvpPresenter(): CityPagerPresenter {
        val geoCityDao = AppDatabase.Instance.get(requireContext()).geoCityDao()
        val selectedCityDao = AppDatabase.Instance.get(requireContext()).selectedCityDao()
        val citySource = SelectedCitySourceImpl(geoCityDao, selectedCityDao)
        return CityPagerPresenter(MainSchedulerProvider.INSTANCE, citySource)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureViewPager()
        configureDrawer()
        presenter.provideCities()
    }

    override fun onStop() {
        super.onStop()
        currentPage = fragmentCityPagerViewPager.currentItem
    }

    private fun configureDrawer() {
        fragmentCityPagerDrawerList.apply {
            adapter = DrawerListAdapter { position ->
                setCurrentPage(position, true)
                closeDrawer()
            }
            addItemDecoration(getDivider())
        }
    }

    private fun setCurrentPage(page: Int, smoothScroll: Boolean) {
        fragmentCityPagerViewPager.setCurrentItem(page, smoothScroll)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    private fun closeDrawer() {
        fragmentCityPagerViewDrawer.closeDrawer(GravityCompat.START)
    }

    private fun openDrawer() {
        fragmentCityPagerViewDrawer.openDrawer(GravityCompat.START)
    }

    private fun configureViewPager() {
        fragmentCityPagerViewPager.apply {
            adapter = CityPagerAdapter(this@CityPagerFragment)
            registerOnPageChangeCallback(pagerScrollListener)
        }
    }

    override fun submitList(items: List<CityForForecastData>) {
        fragmentCityPagerViewPager.apply {
            (adapter as CityPagerAdapter).submitList(items)
            setCurrentPage(currentPage, false)
        }
        (fragmentCityPagerDrawerList.adapter as DrawerListAdapter).apply {
            submitList(items)
            setActivatedItem(currentPage)
        }
    }

    override fun onClickAddCity() {
        listener?.navigateTo(Screen.Factory.listCities())
    }


    override fun onClickOpenDrawer() {
        openDrawer()
    }
}
