/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.gmail.nigtime456.weatherapplication.screen.savedlocations

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.util.ThemeUtils
import com.gmail.nigtime456.weatherapplication.common.util.list.ColorDividerDecoration
import com.gmail.nigtime456.weatherapplication.common.util.list.ItemTouchController
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.common.NavigationController
import com.gmail.nigtime456.weatherapplication.screen.common.Screen
import com.gmail.nigtime456.weatherapplication.screen.savedlocations.list.SavedLocationsAdapter
import com.gmail.nigtime456.weatherapplication.screen.search.SearchCityFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragmet_saved_locations.*


class SavedLocationsFragment :
    BaseFragment<SavedLocationView, SavedLocationsPresenter, NavigationController>(R.layout.fragmet_saved_locations),
    SavedLocationView, SearchCityFragment.TargetFragment {

    interface TargetFragment {
        fun onLocationSelected(position: Int)
    }

    private var itemTouchHelper: ItemTouchHelper? = null
    private var undoSnackbar: Snackbar? = null

    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        savedLocationsRecycler?.let { recyclerView ->
            val scrollOffset = recyclerView.computeVerticalScrollOffset()
            savedLocationsAppbar.isSelected = scrollOffset > 0
        }
    }

    private val adapterListener = object : SavedLocationsAdapter.Listener {
        override fun startDragItem(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper?.startDrag(viewHolder)
        }

        override fun onItemSwiped(swiped: SavedLocation, position: Int) {
            presenter.onItemSwiped(swiped, position)
        }

        override fun onItemsMoved(
            moved: SavedLocation,
            movedPosition: Int,
            target: SavedLocation,
            targetPosition: Int
        ) {
            presenter.onItemsMoved(moved, movedPosition, target, targetPosition)
        }

        override fun onMovementComplete() {
            presenter.onMovementComplete()
        }

        override fun onItemClick(position: Int) {
            presenter.onItemClick(position)
        }
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): BasePresenterProvider<SavedLocationsPresenter> {
        return getRelatedViewModel()
    }

    private fun getRelatedViewModel(): SavedLocationPresenterProvider {
        return ViewModelProvider(this).get(SavedLocationPresenterProvider::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        itemTouchHelper = null
    }

    override fun onStart() {
        super.onStart()
        savedLocationsRecycler.viewTreeObserver.addOnScrollChangedListener(liftScrollListener)
    }

    override fun onStop() {
        super.onStop()
        savedLocationsRecycler.viewTreeObserver.removeOnScrollChangedListener(liftScrollListener)
    }

    private fun initViews() {
        setupRecycler()
        setupAppBar()
    }

    private fun setupAppBar() {
        savedLocationsToolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menuFindCity -> {
                        presenter.onMenuAddClick()
                    }
                    R.id.menuAddLocation -> {
                        showToast(R.string.todo)
                    }
                }
                true
            }
            setNavigationOnClickListener {
                presenter.onNavigationButtonClick()
            }
        }

    }

    private fun setupRecycler() {
        savedLocationsRecycler.apply {
            val listAdapter = SavedLocationsAdapter(adapterListener, getRelatedViewModel())
            adapter = listAdapter
            initTouchGestures(listAdapter)
            addItemDecoration(getDivider())
            itemTouchHelper?.attachToRecyclerView(this)
        }
    }

    private fun initTouchGestures(listAdapter: SavedLocationsAdapter) {
        val itemTouchController = ItemTouchController(listAdapter)
        itemTouchHelper = ItemTouchHelper(itemTouchController)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeUtils.getAttrColor(requireContext(), R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    override fun showProgressLayout() {
        savedLocationsViewSwitcher.switchTo(0, false)
    }

    override fun showEmptyLayout() {
        savedLocationsViewSwitcher.switchTo(2, true)
    }

    override fun showListLayout() {
        savedLocationsViewSwitcher.switchTo(1, true)
    }

    override fun showDialogAboutEmptyList() {
        //TODO должен диалог отображаться или типо того
        Toast.makeText(requireContext(), "Список не должен быть пустым!", Toast.LENGTH_LONG).show()
    }

    override fun showUndoDeleteSnack(duration: Int) {
        undoSnackbar =
            Snackbar.make(savedLocationsRootView, R.string.saved_locations_removed, duration)
                .apply {
                    setAction(R.string.saved_locations_undo) { presenter.onUndoDeleteClick() }
                    show()
                }
    }

    override fun hideUndoDeleteSnack() {
        undoSnackbar?.dismiss()
        undoSnackbar = null
    }

    override fun submitList(items: List<SavedLocation>) {
        (savedLocationsRecycler.adapter as SavedLocationsAdapter).submitList(items, true)
    }

    override fun notifyItemInserted(position: Int) {
        savedLocationsRecycler.adapter?.notifyItemInserted(position)
    }

    override fun scrollToPosition(position: Int) {
        savedLocationsRecycler.smoothScrollToPosition(position)
    }

    override fun delayScrollToPosition(position: Int) {
        //Т.к. из за DiffUtil список формирмуется с задержкой
        savedLocationsRecycler.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                savedLocationsRecycler.smoothScrollToPosition(position)
                savedLocationsRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    override fun setSelectionResult(position: Int) {
        if (targetFragment is TargetFragment) {
            (targetFragment as TargetFragment).onLocationSelected(position)
        }
    }

    override fun navigateToPreviousScreen() {
        attachedListener?.toPreviousScreen()
    }

    override fun navigateToSearchCityScreen() {
        attachedListener?.navigateTo(Screen.Factory.searchCity(this))
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInserted(position)
    }

}
