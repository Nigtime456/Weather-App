/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.locations

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.base.BaseActivity
import com.github.nigtime456.weather.screen.locations.di.DaggerLocationsComponent
import com.github.nigtime456.weather.screen.locations.di.LocationsModule
import com.github.nigtime456.weather.screen.locations.list.LocationModel
import com.github.nigtime456.weather.screen.locations.list.LocationViewHolderPresenterFactory
import com.github.nigtime456.weather.screen.search.SearchActivity
import com.github.nigtime456.weather.ui.list.HorizontalDividerDecoration
import com.github.nigtime456.weather.utils.ui.getColorFromAttr
import com.github.nigtime456.weather.utils.ui.getDimension
import com.github.nigtime456.weather.utils.ui.showSnackBar
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_locations.*
import javax.inject.Inject

class LocationsActivity : BaseActivity(),
    LocationsContract.View,
    FlexibleAdapter.OnItemMoveListener,
    FlexibleAdapter.OnUpdateListener,
    FlexibleAdapter.OnItemClickListener, Toolbar.OnMenuItemClickListener {

    companion object {
        private const val REQUEST_INSERT = 0

        const val EXTRA_SELECTED_POSITION = "weatherapplication.locations.selected_position"

        fun getIntent(context: Context): Intent {
            return Intent(context, LocationsActivity::class.java)
        }
    }

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: LocationsContract.Presenter

    @Inject
    lateinit var itemPresenterFactory: LocationViewHolderPresenterFactory

    private lateinit var locationsAdapter: FlexibleAdapter<LocationModel>

    //</editor-fold>

    //<editor-fold desc="lifecycle">

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)
        setResult(Activity.RESULT_CANCELED)
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

    //</editor-fold>

    //<editor-fold desc="initialization">
    override fun initDi(appComponent: AppComponent) {
        DaggerLocationsComponent.builder()
            .appComponent(appComponent)
            .locationsModule(LocationsModule(this))
            .build()
            .inject(this)
    }

    private fun initViews() {
        initList()
        initAppBar()
        initAdapter()
    }

    private fun initAppBar() {
        activity_locations_toolbar.apply {
            setOnMenuItemClickListener(this@LocationsActivity)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initList() {
        locationsAdapter = FlexibleAdapter(null, this)
        activity_locations_recycler.apply {
            adapter = locationsAdapter
            addItemDecoration(getDivider())
            recycledViewPool.setMaxRecycledViews(LocationModel.VIEW_TYPE, 10)
            setHasFixedSize(true)
        }
    }

    private fun initAdapter() {
        locationsAdapter.apply {
            isHandleDragEnabled = true
        }
    }

    private fun getDivider(): HorizontalDividerDecoration {
        val dividerColor = getColorFromAttr(R.attr.dividerColor)
        val dividerSize = getDimension(R.dimen.divider_size)
        return HorizontalDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    //</editor-fold>

    //<editor-fold desc="ui events">

    override fun onBackPressed() {
        presenter.backPressed()
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

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_city -> {
                presenter.clickAddCity()
            }

            R.id.menu_add_Location -> {
                Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show()
            }

        }
        return true
    }

    //</editor-fold>

    //<editor-fold desc = "adapter events">

    override fun onActionStateChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            //drag уже начался и завершился. Сохраним позиции
            val items = locationsAdapter.currentItems.map { item -> item.location }
            presenter.updateItems(items)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        //nothing
    }

    override fun shouldMoveItem(fromPosition: Int, toPosition: Int): Boolean {
        return true
    }

    override fun onItemClick(view: View, position: Int): Boolean {
        when (view.id) {
            R.id.item_location_remove -> {
                locationsAdapter.getItem(position)?.let {
                    locationsAdapter.removeItem(position)
                    presenter.removeItem(it.location)
                }
            }

            R.id.item_location_layout -> {
                presenter.clickItem(position)
            }
        }

        return false
    }

    override fun onUpdateEmptyView(size: Int) {
        presenter.onListUpdated()
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun showProgressLayout() {
        activity_locations_view_switcher.switchTo(0, true)
    }

    override fun showEmptyLayout() {
        activity_locations_view_switcher.switchTo(2, true)
    }

    override fun showListLayout() {
        activity_locations_view_switcher.switchTo(1, true)
    }

    override fun showDialogEmptyList() {
        LocationsEmptyListDialog().show(supportFragmentManager, null)
    }

    override fun showMessageRemoved() {
        activity_locations_root_view.showSnackBar(R.string.activity_locations_removed)
    }

    override fun showSearchScreen() {
        //TODO transition
        startActivityForResult(
            SearchActivity.getIntent(this),
            REQUEST_INSERT,
            getTransitionOptions()
        )
    }

    override fun showLocations(items: List<SavedLocation>) {
        locationsAdapter.updateDataSet(
            LocationModel.mapList(items, itemPresenterFactory),
            true
        )
    }

    override fun scrollTo(position: Int) {
        activity_locations_recycler.smoothScrollToPosition(position)
    }

    override fun setSelectResult(position: Int) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(EXTRA_SELECTED_POSITION, position)
        )
    }

    override fun finishView() {
        finish()
    }

    //</editor-fold>
}
