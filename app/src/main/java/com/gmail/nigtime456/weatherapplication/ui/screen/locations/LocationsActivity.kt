/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.ColorDividerDecoration
import com.gmail.nigtime456.weatherapplication.ui.list.ItemTouchController
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.di.DaggerLocationsComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.di.LocationsModule
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.list.LocationItemPresenterFactory
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.list.LocationsAdapter
import com.gmail.nigtime456.weatherapplication.ui.screen.search.SearchActivity
import com.gmail.nigtime456.weatherapplication.ui.util.ThemeUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_locations.*
import javax.inject.Inject


class LocationsActivity : BaseActivity(), LocationsContract.View {

    companion object {
        private const val REQUEST_INSERT = 0

        const val EXTRA_SELECTED_POSITION = "weatherapplication.screen.locations.selected_position"

        fun getIntent(context: Context): Intent {
            return Intent(context, LocationsActivity::class.java)
        }
    }


    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        locationsRecycler?.let { recyclerView ->
            val scrollOffset = recyclerView.computeVerticalScrollOffset()
            locationsAppbar.isSelected = scrollOffset > 0
        }
    }

    private val adapterListener: LocationsAdapter.Listener = object : LocationsAdapter.Listener {
        override fun startDragItem(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }

        override fun onItemSwiped(swiped: SavedLocation, position: Int) {
            presenter.swipeItem(swiped, position)
        }

        override fun onItemsMoved(
            moved: SavedLocation,
            movedPosition: Int,
            target: SavedLocation,
            targetPosition: Int
        ) {
            presenter.moveItems(moved, movedPosition, target, targetPosition)
        }

        override fun onMovementComplete() {
            presenter.completeMovement()
        }

        override fun onItemClick(position: Int) {
            presenter.clickItem(position)
        }
    }

    @Inject
    lateinit var presenter: LocationsContract.Presenter

    @Inject
    lateinit var itemPresenterFactory: LocationItemPresenterFactory

    @Inject
    lateinit var rxAsyncDiffer: RxAsyncDiffer

    private val locationsAdapter by lazy {
        LocationsAdapter(
            adapterListener,
            itemPresenterFactory,
            rxAsyncDiffer
        )
    }

    private val itemTouchHelper by lazy {
        ItemTouchHelper(ItemTouchController(locationsAdapter))
    }
    private var undoSnackbar: Snackbar? = null

    override fun onBackPressed() {
        presenter.backPressed()
    }

    override fun initDi(appComponent: AppComponent) {
        DaggerLocationsComponent.builder()
            .appComponent(appComponent)
            .locationsModule(LocationsModule(this))
            .build()
            .inject(this)
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

    private fun initViews() {
        initList()
        initAppBar()
    }

    private fun initAppBar() {
        setSupportActionBar(locationsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initList() {
        locationsRecycler.apply {
            viewTreeObserver.addOnScrollChangedListener(liftScrollListener)
            adapter = locationsAdapter
            itemTouchHelper.attachToRecyclerView(this)
            addItemDecoration(getDivider())
        }
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeUtils.getAttrColor(this, R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.locations, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.menuFindCity -> {
                presenter.clickAddCity()
            }
            R.id.menuAddLocation -> {
                Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
            }
        }
        return true
    }


    override fun showProgressLayout() {
        locationsViewSwitcher.switchTo(0, false)
    }

    override fun showEmptyLayout() {
        locationsViewSwitcher.switchTo(2, true)
    }

    override fun showListLayout() {
        locationsViewSwitcher.switchTo(1, true)
    }

    override fun showMessageAboutEmptyList() {
        LocationsEmptyListDialog().show(supportFragmentManager, null)
    }

    override fun showUndoDeleteSnack(duration: Int) {
        undoSnackbar = Snackbar.make(
            locationsRootView,
            R.string.locations_removed,
            duration
        ).apply {
            setAction(R.string.locations_undo) { presenter.clickUndoDelete() }
            show()
        }
    }

    override fun hideUndoDeleteSnack() {
        undoSnackbar?.dismiss()
    }

    override fun showLocations(items: List<SavedLocation>, scrollToPosition: Int) {
        locationsAdapter.submitList(items) {
            locationsRecycler.smoothScrollToPosition(scrollToPosition)
        }
    }

    override fun showLocations(items: List<SavedLocation>) {
        locationsAdapter.submitList(items)
    }

    override fun notifyItemInserted(position: Int) {
        locationsRecycler.adapter?.notifyItemInserted(position)
    }

    override fun scrollToPosition(position: Int) {
        locationsRecycler.smoothScrollToPosition(position)
    }

    override fun setSelectResult(position: Int) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(EXTRA_SELECTED_POSITION, position)
        )
    }

    override fun showSearchScreen() {
        startActivityForResult(SearchActivity.getIntent(this), REQUEST_INSERT)
    }

    override fun finishView() {
        onBackPressed()
    }
}
