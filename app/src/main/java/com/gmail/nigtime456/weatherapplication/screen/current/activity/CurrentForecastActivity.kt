/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.current.activity

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
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.screen.current.activity.di.CurrentForecastHostModule
import com.gmail.nigtime456.weatherapplication.screen.current.activity.di.DaggerCurrentForecastHostComponent
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.CurrentForecastFragment
import com.gmail.nigtime456.weatherapplication.screen.locations.LocationsActivity
import com.gmail.nigtime456.weatherapplication.screen.search.SearchActivity
import com.gmail.nigtime456.weatherapplication.screen.settings.SettingsActivity
import com.gmail.nigtime456.weatherapplication.tools.ui.getColorFromAttr
import com.google.android.material.navigation.NavigationView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.activity_current_forecast.*
import javax.inject.Inject

class CurrentForecastActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    CurrentForecastActivityContract.View,
    CurrentForecastFragment.ParentActivity {

    companion object {
        private const val EXTRA_PRESENTER_STATE = "weatherapplication.screen.current.activity.state"
        private const val PAGE_LIMIT = 1
        private const val REQUEST_INSERT = 0
        private const val REQUEST_SELECT = 1

        fun getIntent(context: Context) = Intent(context, CurrentForecastActivity::class.java)
    }

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: CurrentForecastActivityContract.Presenter

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
        setContentView(R.layout.activity_current_forecast)
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
    //</editor-fold>

    //<editor-fold desc = "ui events">

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAboutApp -> {
                presenter.clickAboutApp()
            }

            R.id.menuChangeLocations -> {
                presenter.clickEditLocations()
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

    private fun closeDrawer() {
        currentActivityDrawer.closeDrawer(GravityCompat.START)
    }

    //</editor-fold>

    //<editor-fold desc = "initialization">

    override fun initDi(appComponent: AppComponent) {
        DaggerCurrentForecastHostComponent.builder()
            .appComponent(appComponent)
            .currentForecastHostModule(CurrentForecastHostModule(this))
            .build()
            .inject(this)
    }

    private fun initViews() {
        initNavView()
        initViewPager()
        initIndicator()
    }

    private fun initNavView() {
        currentActivityNavView.setNavigationItemSelectedListener(this)
    }

    private fun initIndicator() {
        currentForecastActivityIndicator
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setSliderColor(
                getColorFromAttr(R.attr.colorAccent),
                getColorFromAttr(R.attr.colorAccent)
            )
            .setSliderHeight(50f)
            .setSliderWidth(50f)
            .setSlideMode(IndicatorSlideMode.WORM)
            .setupWithViewPager(currentActivityViewPager)
    }

    @SuppressLint("WrongConstant")
    private fun initViewPager() {
        currentActivityViewPager.apply {
            offscreenPageLimit = PAGE_LIMIT
            registerOnPageChangeCallback(pageScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    //</editor-fold>

    //<editor-fold desc = "fragment's callbacks">

    override fun requestSearchScreen() {
        startActivityForResult(SearchActivity.getIntent(this), REQUEST_INSERT)
    }

    override fun requestOpenDrawer() {
        currentActivityDrawer.openDrawer(GravityCompat.START)
    }

    override fun requestDailyForecastScreen(location: SavedLocation, dayIndex: Int) {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()
    }
    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun showPages(items: List<SavedLocation>) {
        //нужно для правильного восстановления фрагментов
        if (currentActivityViewPager.adapter == null) {
            currentActivityViewPager.adapter = CurrentForecastPageAdapter(this, items)
        } else {
            (currentActivityViewPager.adapter as CurrentForecastPageAdapter).submitList(items)
        }
    }

    override fun showNavView(items: List<SavedLocation>) {
        currentActivityNavView.menu
            .findItem(R.id.menuListLocations)
            .subMenu
            .apply {
                clear()
                setupNavItems(items)
            }
    }

    private fun SubMenu.setupNavItems(items: List<SavedLocation>) {
        items.forEachIndexed { index, location ->
            val menuItem = add(0, index, 0, location.getName())
            menuItem.setIcon(R.drawable.ic_city)
            menuItem.isCheckable = true
        }
    }

    override fun setCurrentPage(page: Int) {
        currentActivityViewPager.setCurrentItem(page, false)
    }

    override fun setCurrentNavItem(index: Int) {
        currentActivityNavView.setCheckedItem(index)
    }

    override fun showDialogAboutApp() {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()
    }

    override fun showLocationsScreen() {
        startActivityForResult(LocationsActivity.getIntent(this), REQUEST_SELECT)
    }

    override fun showSettingsScreen() {
        startActivity(SettingsActivity.getIntent(this))
    }

    override fun showNotificationsScreen() {
        Toast.makeText(this, R.string.todo, Toast.LENGTH_SHORT).show()
    }

    //</editor-fold>


}
