/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.pager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.SubMenu
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.CurrentForecastFragment
import com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.di.CurrentForecastHostModule
import com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.di.DaggerCurrentForecastHostComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.LocationsActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.search.SearchActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_current_forecast.*
import javax.inject.Inject

class CurrentForecastActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    CurrentForecastHostContract.View,
    CurrentForecastFragment.Parent {

    companion object {
        private const val PAGE_LIMIT = 1
        private const val REQUEST_INSERT = 0
        private const val REQUEST_SELECT = 1

        fun getIntent(context: Context) = Intent(context, CurrentForecastActivity::class.java)
    }

    @Inject
    lateinit var presenter: CurrentForecastHostContract.Presenter

    private val pageAdapter by lazy { LocationsPageAdapter(this) }

    private val pageScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            presenter.scrollPage(position)
        }
    }

    override fun initDi(appComponent: AppComponent) {
        DaggerCurrentForecastHostComponent.builder()
            .appComponent(appComponent)
            .currentForecastHostModule(CurrentForecastHostModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_forecast)
        initViews()
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
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAboutApp -> {
                presenter.clickAboutApp()
            }
            R.id.menuChangeLocations -> {
                presenter.clickChangeLocations()
            }
            R.id.menuSettings -> {
                presenter.clickSettings()
            }
            R.id.menuWeatherNotifications -> {
                presenter.clickWeatherNotifications()
            }
            else -> {
                presenter.clickNavItem(item.itemId)
            }
        }
        closeDrawer()
        return true
    }

    private fun initViews() {
        initNavView()
        initViewPager()
    }

    private fun initNavView() {
        currentHostNavView.setNavigationItemSelectedListener(this)
    }

    @SuppressLint("WrongConstant")
    private fun initViewPager() {
        currentHostViewPager.apply {
            offscreenPageLimit = PAGE_LIMIT
            adapter = pageAdapter
            registerOnPageChangeCallback(pageScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    private fun closeDrawer() {
        currentHostDrawer.closeDrawer(GravityCompat.START)
    }

    override fun showPages(items: List<SavedLocation>) {
        pageAdapter.submitList(items)
    }

    override fun showNavView(items: List<SavedLocation>) {
        currentHostNavView.menu
            .findItem(R.id.menuListLocations)
            .subMenu
            .apply {
                clear()
                setupNavItems(items)
            }
    }

    private fun SubMenu.setupNavItems(items: List<SavedLocation>) {
        items.forEachIndexed { index, cityForForecast ->
            val menuItem = add(0, index, 0, cityForForecast.getName())
            menuItem.setIcon(R.drawable.ic_city)
            menuItem.isCheckable = true
        }
    }

    override fun setCurrentPage(page: Int) {
        currentHostViewPager.setCurrentItem(page, false)
    }

    override fun setCurrentNavItem(index: Int) {
        currentHostNavView.setCheckedItem(index)
    }

    override fun showDialogAboutApp() {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()
    }

    override fun showChangeLocationsScreen() {
        startActivityForResult(LocationsActivity.getIntent(this), REQUEST_SELECT)
    }

    override fun showSettingsScreen() {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()

    }

    override fun showWeatherNotificationScreen() {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()
    }

    override fun clickAddCity() {
        startActivityForResult(SearchActivity.getIntent(this), REQUEST_INSERT)
    }

    override fun openDrawer() {
        currentHostDrawer.openDrawer(GravityCompat.START)
    }

    override fun requestDailyForecast(location: SavedLocation, dayIndex: Int) {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()
    }
}
