/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.daily.host

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.common.NavigationController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_daily_pages.*

class DailyHostFragment :
    BaseFragment<DailyHostView, DailyHostPresenter, NavigationController>(R.layout.fragment_daily_pages),
    DailyHostView {

    companion object {
        private const val EXTRA_DAY_INDEX = "weatherapplication.screen.daily_host.day_index"
        private const val EXTRA_LOCATION = "weatherapplication.screen.daily_host.location"

        fun newInstance(savedLocation: SavedLocation, dayIndex: Int): DailyHostFragment {
            return DailyHostFragment().apply {
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
            R.id.dailyHostSwipeRefresh
            presenter.setCurrentPage(position)
        }
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterFactory(): BasePresenterProvider<DailyHostPresenter> {
        val currentLocation = arguments?.getParcelable<SavedLocation>(EXTRA_LOCATION)
            ?: error("need SavedLocation")
        val vmFactory = DailyHostPresenterProvider.Factory(currentLocation)
        return ViewModelProvider(this, vmFactory)
            .get(DailyHostPresenterProvider::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDayIndex()
        initViews()
    }

    override fun onStart() {
        super.onStart()
        dailyHostViewPager.registerOnPageChangeCallback(pagerCallback)
    }

    override fun onStop() {
        super.onStop()
        dailyHostViewPager.unregisterOnPageChangeCallback(pagerCallback)
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
        dailyHostToolbar.setNavigationOnClickListener {
            presenter.onNavigationClick()
        }
    }

    private fun setupSwipeRefresh() {
        dailyHostSwipeRefresh.setOnRefreshListener(presenter::onRequestRefresh)
    }

    override fun setLocation(title: String) {
        dailyHostToolbar.title = title
    }

    override fun setViewPager(location: SavedLocation) {
        dailyHostViewPager.adapter = DailyHostPageAdapter(this, location)
    }

    override fun setTabLayout(titles: List<String>) {
        TabLayoutMediator(
            dailyHostTabLayout,
            dailyHostViewPager,
            false
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
    }

    override fun setPage(page: Int) {
        dailyHostViewPager.setCurrentItem(page, false)
    }

    override fun stopRefreshing() {
        dailyHostSwipeRefresh.isRefreshing = false
    }

    override fun showErrorMessage() {
        showToast(R.string.network_error)
    }

    override fun navigateToPreviousScreen() {
        attachedListener?.toPreviousScreen()
    }

}