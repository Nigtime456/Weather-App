/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.ThemeHelper
import com.nigtime.weatherapplication.common.utility.list.ColorDividerDecoration
import com.nigtime.weatherapplication.common.utility.list.ItemTouchController
import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import com.nigtime.weatherapplication.screen.common.Screen
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.wishlist.list.WishCitiesListAdapter
import kotlinx.android.synthetic.main.fragmet_wish_list.*


class WishCitiesFragment :
    BaseFragment<WishCitiesView, WishCitiesPresenter, NavigationController>(R.layout.fragmet_wish_list),
    WishCitiesView, SearchCityFragment.TargetFragment {

    interface TargetFragment {
        fun onCitySelected(position: Int)
    }

    private var itemTouchHelper: ItemTouchHelper? = null
    private var undoSnackbar: Snackbar? = null

    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        wishRecycler?.let { recyclerView ->
            val scrollOffset = recyclerView.computeVerticalScrollOffset()
            wishAppbar.isSelected = scrollOffset > 0
        }
    }

    private val adapterListener = object : WishCitiesListAdapter.Listener {
        override fun startDragItem(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper?.startDrag(viewHolder)
        }

        override fun onItemSwiped(swiped: WishCity, position: Int) {
            presenter.onItemSwiped(swiped, position)
        }

        override fun onItemsMoved(
            moved: WishCity,
            movedPosition: Int,
            target: WishCity,
            targetPosition: Int
        ) {
            presenter.onItemsMoved(moved, movedPosition, target, targetPosition)
        }

        override fun onMovementComplete() {
            presenter.saveListChanges()
        }

        override fun onItemClick(position: Int) {
            presenter.onItemClick(position)
        }
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): PresenterProvider<WishCitiesPresenter> {
        return ViewModelProvider(this).get(WishCitiesPresenterProvider::class.java)
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
        wishRecycler.viewTreeObserver.addOnScrollChangedListener(liftScrollListener)
    }

    override fun onStop() {
        super.onStop()
        wishRecycler.viewTreeObserver.removeOnScrollChangedListener(liftScrollListener)
    }

    private fun initViews() {
        setupRecycler()
        setupAppBar()
    }

    private fun setupAppBar() {
        wishToolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menuFindCity) {
                    presenter.onMenuAddClick()
                }
                true
            }
            setNavigationOnClickListener {
                presenter.onNavigationButtonClick()
            }
        }

    }

    private fun setupRecycler() {
        wishRecycler.apply {
            val listAdapter = WishCitiesListAdapter(adapterListener)
            adapter = listAdapter
            initTouchGestures(listAdapter)
            addItemDecoration(getDivider())
            itemTouchHelper?.attachToRecyclerView(this)
        }
    }

    private fun initTouchGestures(listAdapter: WishCitiesListAdapter) {
        val itemTouchController = ItemTouchController(listAdapter)
        itemTouchHelper = ItemTouchHelper(itemTouchController)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    override fun submitList(items: List<WishCity>) {
        (wishRecycler.adapter as WishCitiesListAdapter).submitList(items)
    }

    override fun showProgressLayout() {
        wishViewSwitcher.switchTo(0, false)
    }

    override fun showListLayout() {
        wishViewSwitcher.switchTo(1, true)
    }

    override fun showEmptyLayout() {
        wishViewSwitcher.switchTo(2, true)
    }

    override fun notifyItemInserted(position: Int) {
        wishRecycler.adapter?.notifyItemInserted(position)
    }

    override fun scrollToPosition(position: Int) {
        wishRecycler.smoothScrollToPosition(position)
    }

    override fun delayScrollToPosition(position: Int) {
        wishRecycler.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                wishRecycler.smoothScrollToPosition(position)
                wishRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    override fun showUndoDeleteSnack(duration: Int) {
        undoSnackbar = Snackbar.make(wishRoot, R.string.wish_city_removed, duration).apply {
            setAction(R.string.wish_undo) { presenter.onUndoDeleteClick() }
            show()
        }
    }

    override fun hideUndoDeleteSnack() {
        undoSnackbar?.dismiss()
        undoSnackbar = null
    }

    override fun showDialogAboutEmptyList() {
        //TODO должен диалог отображаться или типо того
        Toast.makeText(requireContext(), "Список не должен быть пустым!", Toast.LENGTH_LONG).show()
    }

    override fun setSelectionResult(position: Int) {
        if (targetFragment is TargetFragment) {
            (targetFragment as TargetFragment).onCitySelected(position)
        }
    }

    override fun navigateToPreviousScreen() {
        parentListener?.toPreviousScreen()
    }

    override fun navigateToSearchCityScreen() {
        parentListener?.navigateTo(Screen.Factory.searchCity(this))
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInserted(position)
    }
}
