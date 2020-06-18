/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.screen.daily.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.base.BaseActivity
import com.github.nigtime456.weather.screen.daily.activity.di.DaggerDailyActivityComponent
import com.github.nigtime456.weather.screen.daily.activity.di.DailyActivityModule
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_daily.*
import javax.inject.Inject

class DailyActivity : BaseActivity(), DailyActivityContract.View {

    companion object {
        private const val EXTRA_LOCATION = "weatherapplication.daily.activity.location"
        private const val EXTRA_PART_OF_DAY = "weatherapplication.daily.activity.part_of_day"
        private const val EXTRA_DAY_INDEX = "weatherapplication.daily.activity.day_index"

        fun getIntent(
            context: Context,
            location: SavedLocation,
            partOfDay: PartOfDay,
            dayIndex: Int
        ): Intent {
            return Intent(context, DailyActivity::class.java)
                .putExtra(EXTRA_LOCATION, location)
                .putExtra(EXTRA_PART_OF_DAY, partOfDay as Parcelable)
                .putExtra(EXTRA_DAY_INDEX, dayIndex)
        }
    }

    //<editor-fold desc = "fields">
    @Inject
    lateinit var presenter: DailyActivityContract.Presenter
    private val pageAdapter = DailyPageAdapter(this)
    private val pageScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            presenter.scrollPage(position)
        }
    }

    //</editor-fold>

    //<editor-fold desc = "lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)
        initViews()
        presenter.start()
        presenter.provideForecast()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.provideForecast()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }
    //</editor-fold>

    //<editor-fold desc = "initialization">
    override fun initDi(appComponent: AppComponent) {
        DaggerDailyActivityComponent.builder()
            .appComponent(appComponent)
            .dailyActivityModule(
                DailyActivityModule(
                    this,
                    getLocation(),
                    getPartOfDay(),
                    getDayIndex()
                )
            )
            .build()
            .inject(this)
    }

    private fun getLocation(): SavedLocation {
        return intent.getParcelableExtra(EXTRA_LOCATION) ?: error("require location")
    }

    private fun getPartOfDay(): PartOfDay {
        return intent.getParcelableExtra(EXTRA_PART_OF_DAY) ?: error("require part of day")
    }

    private fun getDayIndex(): Int {
        return intent.getIntExtra(EXTRA_DAY_INDEX, 0)
    }

    private fun initViews() {
        initAppbar()
        initPullToRefresh()
        initViewPager()
        applyInsets()
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(activity_daily_root) { v, insets ->
            v.setPadding(0, insets.systemWindowInsetTop, 0, 0)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun initAppbar() {
        activity_daily_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initPullToRefresh() {
        activity_daily_refresh_layout.setOnRefreshListener {
            presenter.refreshData()
        }
    }

    private fun initViewPager() {
        activity_daily_view_pager.apply {
            adapter = pageAdapter
            registerOnPageChangeCallback(pageScrollListener)
        }
    }
    //</editor-fold>

    //<editor-fold desc = "MVP">
    override fun showLocationName(location: SavedLocation) {
        activity_daily_toolbar.title = location.name
    }

    override fun showDarkBackground() {
        activity_daily_root.setBackgroundResource(R.drawable.view_pod_night)
    }

    override fun showLightBackground() {
        activity_daily_root.setBackgroundResource(R.drawable.view_pod_day)
    }

    override fun showForecast(forecastList: List<DailyForecast>) {
        pageAdapter.submitList(forecastList)
    }

    override fun showTabs(titles: List<String>) {
        TabLayoutMediator(
            activity_daily_tab_layout,
            activity_daily_view_pager,
            false
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
    }

    override fun setPage(page: Int) {
        activity_daily_view_pager.setCurrentItem(page, false)
    }

    override fun stopRefreshing(success: Boolean) {
        activity_daily_refresh_layout.finishRefresh(success)
    }

    override fun setUpdateTime(time: Long) {
        activity_daily_refresh_header.setUpdateTime(time)
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG)
            .show()
    }

    //</editor-fold>
}