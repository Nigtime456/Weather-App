/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.currently.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.about.AboutActivity
import com.github.nigtime456.weather.screen.base.BaseActivity
import com.github.nigtime456.weather.screen.currently.activity.di.CurrentlyActivityModule
import com.github.nigtime456.weather.screen.currently.activity.di.DaggerCurrentlyActivityComponent
import com.github.nigtime456.weather.screen.currently.fragment.CurrentlyFragment
import com.github.nigtime456.weather.screen.daily.activity.DailyActivity
import com.github.nigtime456.weather.screen.locations.LocationsActivity
import com.github.nigtime456.weather.screen.search.SearchActivity
import com.github.nigtime456.weather.screen.settings.SettingsActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_currently.*
import javax.inject.Inject

class CurrentlyActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    CurrentlyActivityContract.View,
    CurrentlyFragment.ParentActivity, Toolbar.OnMenuItemClickListener {

    companion object {
        private const val EXTRA_PRESENTER_STATE = "weatherapplication.currently.activity.state"
        private const val PAGE_LIMIT = 1
        private const val REQUEST_INSERT = 0
        private const val REQUEST_SELECT = 1

        fun getIntent(context: Context) = Intent(context, CurrentlyActivity::class.java)
    }

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: CurrentlyActivityContract.Presenter

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
        setContentView(R.layout.activity_currently)
        initViews()
        presenter.restoreState(savedInstanceState?.getParcelable(EXTRA_PRESENTER_STATE))
        presenter.loadLocations()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.loadLocations()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_PRESENTER_STATE, presenter.getState())
    }

    //</editor-fold>

    //<editor-fold desc = "initialization">
    override fun initDi(appComponent: AppComponent) {
        DaggerCurrentlyActivityComponent.builder()
            .appComponent(appComponent)
            .currentlyActivityModule(CurrentlyActivityModule(this))
            .build()
            .inject(this)
    }

    private fun initViews() {
        initAppbar()
        initNavView()
        initPager()
        applyInsets()
    }

    private fun initAppbar() {
        activity_currently_toolbar.apply {
            setOnMenuItemClickListener(this@CurrentlyActivity)
            setNavigationOnClickListener {
                presenter.clickNavigationButton()
            }
        }
    }

    private fun initNavView() {
        activity_currently_nav_view.setNavigationItemSelectedListener(this)
    }

    @SuppressLint("WrongConstant")
    private fun initPager() {
        activity_currently_locations_pager.apply {
            offscreenPageLimit = PAGE_LIMIT
            registerOnPageChangeCallback(pageScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(activity_currently_drawer) { _, insets ->
            activity_currently_nav_view.getHeaderView(0)
                .setPadding(0, insets.systemWindowInsetTop, 0, 0)

            activity_currently_main_layout
                .setPadding(0, insets.systemWindowInsetTop, 0, 0)

            insets.consumeSystemWindowInsets()
        }
    }

    //</editor-fold>

    //<editor-fold desc = "ui events">

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED || data == null) {
            //ignore
            return
        }

        when (requestCode) {
            REQUEST_INSERT -> {
                val position = data.getIntExtra(SearchActivity.EXTRA_INSERTED_POSITION, 0)
                presenter.onCityInserted(position)
            }

            REQUEST_SELECT -> {
                val position = data.getIntExtra(LocationsActivity.EXTRA_SELECTED_POSITION, 0)
                presenter.onCitySelected(position)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_city -> {
                presenter.clickAddCity()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about_app -> {
                presenter.clickAboutApp()
            }

            R.id.menu_change_locations -> {
                presenter.clickEditLocations()
            }

            R.id.menu_settings -> {
                presenter.clickSettings()
            }

            else -> {
                presenter.clickNavItem(item.itemId)
            }
        }
        return true
    }

    //</editor-fold>

    //<editor-fold desc = "fragment's callbacks">

    override fun setLocationName(location: SavedLocation) {
        activity_currently_title.text = location.name
    }

    override fun setTimeZone(timeZone: String) {
        activity_currently_text_clock.timeZone = timeZone
    }

    override fun showDailyForecastScreen(
        location: SavedLocation,
        partOfDay: PartOfDay,
        dayIndex: Int
    ) {
        //TODO transition
        startActivity(
            DailyActivity.getIntent(this, location, partOfDay, dayIndex),
            getTransitionOptions()
        )
    }

    override fun setPartOfDay(partOfDay: PartOfDay) {
        //TODO вынести в перезентер
        val index = if (partOfDay == PartOfDay.DAY) 0 else 1
        activity_currently_view_switcher.switchTo(index, true)
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun showLocations(items: List<SavedLocation>) {
        //нужно для правильного восстановления фрагментов
        if (activity_currently_locations_pager.adapter == null) {
            activity_currently_locations_pager.adapter = CurrentlyPageAdapter(this, items)
        } else {
            (activity_currently_locations_pager.adapter as CurrentlyPageAdapter)
                .submitList(items)
        }
    }

    override fun showNavItems(items: List<SavedLocation>) {
        activity_currently_nav_view.menu
            .findItem(R.id.menu_list_locations)
            .subMenu
            .apply {
                clear()
                setupNavItems(items)
            }
    }

    private fun SubMenu.setupNavItems(items: List<SavedLocation>) {
        items.forEachIndexed { index, location ->
            val menuItem = add(0, index, 0, location.name)
            menuItem.setIcon(R.drawable.ic_city)
            menuItem.isCheckable = true
        }
    }

    override fun setCurrentPage(page: Int) {
        activity_currently_locations_pager.setCurrentItem(page, false)
    }

    override fun setCurrentNavItem(index: Int) {
        activity_currently_nav_view.setCheckedItem(index)
    }

    override fun showAboutAppScreen() {
        //TODO transition
        startActivity(AboutActivity.getIntent(this), getTransitionOptions())
    }

    override fun showLocationsScreen() {
        //TODO transition
        startActivityForResult(
            LocationsActivity.getIntent(this),
            REQUEST_SELECT,
            getTransitionOptions()
        )
    }

    override fun showSettingsScreen() {
        //TODO transition
        startActivity(SettingsActivity.getIntent(this), getTransitionOptions())
    }

    override fun showSearchScreen() {
        //TODO transition
        startActivityForResult(
            SearchActivity.getIntent(this),
            REQUEST_INSERT,
            getTransitionOptions()
        )
    }

    override fun openDrawer() {
        activity_currently_drawer.openDrawer(GravityCompat.START)
    }

    override fun closeDrawer() {
        activity_currently_drawer.closeDrawer(GravityCompat.START)
    }

    //</editor-fold>

}
