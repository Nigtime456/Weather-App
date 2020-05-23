/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.dailypages

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import com.nigtime.weatherapplication.screen.common.NavigationController
import kotlinx.android.synthetic.main.fragment_daily_pages.*

class DailyPagesFragment :
    BaseFragment<DailyPagesView, DailyPagesPresenter, NavigationController>(R.layout.fragment_daily_pages),
    DailyPagesView {

    companion object {
        private const val EXTRA_DAY_INDEX = "weatherapplication.screen.daily_pages.day_index"
        private const val EXTRA_LOCATION = "weatherapplication.screen.daily_pages.location"

        fun newInstance(savedLocation: SavedLocation, dayIndex: Int): DailyPagesFragment {
            return DailyPagesFragment().apply {
                require(dayIndex in 0..15) { "invalid day index = $dayIndex" }
                arguments = bundleOf(
                    EXTRA_LOCATION to savedLocation,
                    EXTRA_DAY_INDEX to dayIndex
                )
            }
        }
    }

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            presenter.setCurrentPage(position)
        }
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): BasePresenterProvider<DailyPagesPresenter> {
        val currentLocation = arguments?.getParcelable<SavedLocation>(EXTRA_LOCATION)
            ?: error("need SavedLocation")
        val vmFactory = DailyPagesPresenterProvider.Factory(currentLocation)
        return ViewModelProvider(this, vmFactory)
            .get(DailyPagesPresenterProvider::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDayIndex()
        initViews()
    }

    override fun onStart() {
        super.onStart()
        dailyPagesViewPager.registerOnPageChangeCallback(pagerCallback)
    }

    override fun onStop() {
        super.onStop()
        dailyPagesViewPager.unregisterOnPageChangeCallback(pagerCallback)
    }

    private fun setDayIndex() {
        val dayIndex = arguments?.getInt(EXTRA_DAY_INDEX) ?: 0
        presenter.setCurrentPage(dayIndex)
    }

    private fun initViews() {
        setupToolbar()
        setupSwipeRefresh()
    }

    private fun setupToolbar() {
        dailyPagesToolbar.setNavigationOnClickListener {
            presenter.onNavigationClick()
        }
    }

    private fun setupSwipeRefresh() {
        dailyPagesSwipeRefresh.setOnRefreshListener(presenter::onRequestRefresh)
    }

    override fun setLocation(title: String) {
        dailyPagesToolbar.title = title
    }

    override fun setViewPager(location: SavedLocation) {
        dailyPagesViewPager.adapter = DailyPagesAdapter(this, location)
    }

    override fun setTabLayout(titles: List<String>) {
        TabLayoutMediator(
            dailyPagesTabLayout,
            dailyPagesViewPager,
            false
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
    }

    override fun setPage(page: Int) {
        dailyPagesViewPager.setCurrentItem(page, false)
    }

    override fun stopRefreshing() {
        dailyPagesSwipeRefresh.isRefreshing = false
    }

    override fun showErrorMessage() {
        showToast(R.string.network_error)
    }

    override fun navigateToPreviousScreen() {
        attachedListener?.toPreviousScreen()
    }

}