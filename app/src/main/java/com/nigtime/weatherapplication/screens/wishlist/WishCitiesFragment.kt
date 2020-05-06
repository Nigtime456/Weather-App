/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screens.wishlist

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.helper.ThemeHelper
import com.nigtime.weatherapplication.common.helper.list.ColorDividerDecoration
import com.nigtime.weatherapplication.common.helper.list.LiftOnScrollListener
import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screens.common.BaseFragment
import com.nigtime.weatherapplication.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.screens.common.NavigationController
import com.nigtime.weatherapplication.screens.common.Screen
import com.nigtime.weatherapplication.screens.search.SearchCityFragment
import kotlinx.android.synthetic.main.fragmet_wish_list.*


class WishCitiesFragment : BaseFragment<WishCitiesFragment.Listener>(R.layout.fragmet_wish_list),
    WishCitiesView, SearchCityFragment.TargetFragment {

    interface Listener : NavigationController

    companion object {
        fun newInstance(): WishCitiesFragment = WishCitiesFragment()
    }

    private lateinit var itemTouchHelper: ItemTouchHelper
    private var undoSnackbar: Snackbar? = null
    private lateinit var liftOnScrollListener: LiftOnScrollListener
    private var presenter: WishCitiesPresenter =
        App.INSTANCE.appContainer.wishCitiesContainer.presenter
    private lateinit var listAdapter: WishListAdapter

    private val adapterListener = object : WishListAdapter.Listener {
        override fun onRequestDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }

        override fun onItemSwiped(
            item: WishCity,
            position: Int,
            items: MutableList<WishCity>
        ) {
            presenter.onItemSwiped(item, position, items)
        }

        override fun onItemsMoved(items: List<WishCity>) {
            presenter.onItemsMoved(items)
        }

        override fun onItemClick(position: Int) {
            presenter.onClickItem(position)
        }

    }

    override fun provideListenerClass(): Class<Listener>? = Listener::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureViews()
        presenter.provideCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liftOnScrollListener.release()
    }

    override fun onStop() {
        super.onStop()
        presenter.onFragmentStop()
    }

    private fun configureViews() {
        configureRecycler()
        configureAppBar()
    }

    private fun configureAppBar() {
        wishToolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.menuAdd) {
                    parentListener?.navigateTo(
                        Screen.Factory.searchCity(this@WishCitiesFragment)
                    )
                }
                true
            }
            setNavigationOnClickListener {
                presenter.onClickNavigationButton()
            }
        }

    }

    private fun configureRecycler() {
        listAdapter = WishListAdapter(adapterListener)
        initTouchGestures(listAdapter)

        wishRecycler.apply {
            adapter = listAdapter
            addItemDecoration(getDivider())
            itemTouchHelper.attachToRecyclerView(this)
            setLiftOnScrollAppBar(this)
        }
    }

    private fun setLiftOnScrollAppBar(recyclerView: RecyclerView) {
        liftOnScrollListener = LiftOnScrollListener(recyclerView) { doLift ->
            wishAppbar.isSelected = doLift
        }
    }

    private fun initTouchGestures(listAdapter: WishListAdapter) {
        val itemTouchController = ItemTouchController(listAdapter)
        itemTouchHelper = ItemTouchHelper(itemTouchController)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themePrimaryDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    override fun submitList(items: List<WishCity>) {
        listAdapter.submitList(items)
    }

    override fun showProgressBar() {
        wishViewSwitcher.switchTo(0, true)
    }

    override fun showList() {
        wishViewSwitcher.switchTo(1, false)

    }

    override fun showMessageEmpty() {
        wishViewSwitcher.switchTo(2, true)

    }


    override fun insertItemToList(item: WishCity, position: Int) {
        listAdapter.insertItemToList(item, position)
    }

    override fun scrollListToPosition(position: Int) {
        wishRecycler.smoothScrollToPosition(position)
    }

    override fun delayScrollListToPosition(position: Int) {
        wishRecycler.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                wishRecycler.smoothScrollToPosition(position)
                wishRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    override fun showUndoDeleteSnack() {
        undoSnackbar =
            Snackbar.make(wishRoot, R.string.wish_city_removed, getRemoveDuration().toInt()).apply {
                setAction(R.string.wish_undo) { presenter.onClickUndoDelete() }
                show()
            }
    }

    override fun hideUndoDeleteSnack() {
        undoSnackbar?.dismiss()
        undoSnackbar = null
    }

    override fun navigateToPreviousScreen() {
        parentListener?.toBack()
    }

    override fun navigateToPageScreen(position: Int) {
        //TODO сейчас пересоздается фрагмент, надо бы пересмотреть это поведение.
        parentListener?.navigateTo(Screen.Factory.pager(position))
    }

    override fun showPopupMessageEmptyList() {
        //TODO должен диалог отображаться или типо того
        Toast.makeText(requireContext(), "Список не должен быть пустым!", Toast.LENGTH_LONG).show()
    }

    private fun getRemoveDuration(): Long {
        return resources.getInteger(R.integer.wish_remove_delay).toLong()
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInsertedAt(position)
    }

}
