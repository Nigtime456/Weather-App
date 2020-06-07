/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.locations

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.screen.locations.di.DaggerLocationsComponent
import com.gmail.nigtime456.weatherapplication.screen.locations.di.LocationsModule
import com.gmail.nigtime456.weatherapplication.screen.locations.list.LocationModel
import com.gmail.nigtime456.weatherapplication.screen.locations.list.LocationViewHolderPresenterFactory
import com.gmail.nigtime456.weatherapplication.screen.search.SearchActivity
import com.gmail.nigtime456.weatherapplication.tools.ui.getColorFromAttr
import com.gmail.nigtime456.weatherapplication.tools.ui.getDimension
import com.gmail.nigtime456.weatherapplication.tools.ui.showSnackBar
import com.gmail.nigtime456.weatherapplication.ui.list.ColorDividerDecoration
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_locations.*
import javax.inject.Inject

class LocationsActivity : BaseActivity(),
    LocationsContract.View,
    FlexibleAdapter.OnItemMoveListener,
    FlexibleAdapter.OnUpdateListener,
    LocationModel.OnClickListener {

    companion object {
        private const val REQUEST_INSERT = 0

        const val EXTRA_SELECTED_POSITION = "weatherapplication.screen.locations.selected_position"

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

    override fun onBackPressed() {
        presenter.backPressed()
    }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.locations_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuFindCity -> {
                presenter.clickAddCity()
                true
            }

            R.id.menuAddLocation -> {
                Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
                true
            }

            else -> {
                false
            }
        }
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
        setSupportActionBar(locationsToolbar)
    }

    private fun initList() {
        locationsAdapter = FlexibleAdapter(null)
        locationsRecycler.apply {
            adapter = locationsAdapter
            addItemDecoration(getDivider())
            locationsAppbar.setTarget(this)
            setHasFixedSize(true)
        }
    }

    private fun initAdapter() {
        locationsAdapter.apply {
            isHandleDragEnabled = true
        }
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = getColorFromAttr(R.attr.colorControlHighlight)
        val dividerSize = getDimension(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
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

    override fun onClickItem(position: Int) {
        presenter.clickItem(position)
    }

    override fun onClickDeleteItem(position: Int) {
        locationsAdapter.getItem(position)?.let {
            locationsAdapter.removeItem(position)
            presenter.deleteItem(it.location)
        }
    }

    override fun onUpdateEmptyView(size: Int) {
        presenter.onListUpdated()
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun showProgressLayout() {
        locationsAppbar.setLift(false)
        locationsViewSwitcher.switchTo(0, true)
    }

    override fun showEmptyLayout() {
        locationsAppbar.setLift(false)
        locationsViewSwitcher.switchTo(2, true)
    }

    override fun showListLayout() {
        locationsViewSwitcher.switchTo(1, true)
    }

    override fun showDialogEmptyList() {
        LocationsEmptyListDialog().show(supportFragmentManager, null)
    }

    override fun showMessageDelete() {
        locationsRootView.showSnackBar(R.string.locations_removed)
    }

    override fun showSearchScreen() {
        startActivityForResult(SearchActivity.getIntent(this), REQUEST_INSERT)
    }

    override fun showLocations(items: List<SavedLocation>) {
        locationsAdapter.updateDataSet(
            LocationModel.mapList(items, itemPresenterFactory, this),
            false
        )
    }

    override fun scrollTo(position: Int) {
        locationsRecycler.smoothScrollToPosition(position)
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
