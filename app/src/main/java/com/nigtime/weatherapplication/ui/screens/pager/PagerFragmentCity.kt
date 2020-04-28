/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.ui.screens.pager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.CityForForecast
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.ui.tools.ThemeHelper
import com.nigtime.weatherapplication.ui.tools.list.ColorDividerDecoration
import com.nigtime.weatherapplication.utility.di.DataRepositoryFactory
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import kotlinx.android.synthetic.main.fragment_pager.*
import kotlinx.android.synthetic.main.fragment_pager_drawer.*


class PagerFragmentCity : BaseFragment<PagerFragmentCity.ActivityListener>(),
    PagerCityView,
    CurrentForecastFragment.ParentListener {

    interface ActivityListener : NavigationController

    companion object {
        private const val EXTRA_PAGE = "com.nigtime.weatherapp.city_pager.page"

        fun newInstance(page: Int): PagerFragmentCity {
            return PagerFragmentCity().apply {
                arguments = bundleOf(EXTRA_PAGE to page)
            }
        }
    }

    private lateinit var cityPresenter: PagerCityPresenter
    private var currentPage = 0
    private lateinit var listAdapter: DrawerListAdapter
    private lateinit var pagerCityAdapter: PagerCityAdapter

    private val pagerScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            (pagerDrawerList.adapter as DrawerListAdapter).setActivatedItem(position)
        }
    }

    override fun provideListenerClass(): Class<ActivityListener>? = ActivityListener::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cityPresenter = PagerCityPresenter(
            MainSchedulerProvider.INSTANCE,
            DataRepositoryFactory.getForecastCitiesRepository()
        )
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
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityPresenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureViews()
        cityPresenter.provideCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
    }

    override fun onStop() {
        super.onStop()
        currentPage = pagerViewPager.currentItem
    }

    private fun configureViews() {
        listAdapter = DrawerListAdapter { position ->
            setCurrentPage(position, true)
            closeDrawer()
        }
        pagerCityAdapter = PagerCityAdapter(this)
        configureViewPager()
        configureDrawer()
    }

    private fun configureDrawer() {
        pagerDrawerList.apply {
            adapter = listAdapter
            addItemDecoration(getDivider())
        }
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

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    private fun closeDrawer() {
        pagerDrawer.closeDrawer(GravityCompat.START)
    }

    private fun openDrawer() {
        pagerDrawer.openDrawer(GravityCompat.START)
    }

    override fun submitList(items: List<CityForForecast>) {
        pagerCityAdapter.submitList(items)
        listAdapter.submitList(items)
        listAdapter.setActivatedItem(currentPage)
        setCurrentPage(currentPage, false)
    }

    override fun onClickAddCity() {
        parentListener?.navigateTo(Screen.Factory.wishList())
    }


    override fun onClickOpenDrawer() {
        openDrawer()
    }
}
