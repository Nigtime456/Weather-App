/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.screens.wishlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.WishCity
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.ui.tools.ThemeHelper
import com.nigtime.weatherapplication.ui.tools.list.ColorDividerDecoration
import com.nigtime.weatherapplication.ui.tools.list.LiftOnScrollListener
import com.nigtime.weatherapplication.utility.di.DataRepositoryFactory
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.utility.rx.RxDelayedMessageDispatcher
import kotlinx.android.synthetic.main.fragmet_wish_list.*


class WishCitiesFragment : BaseFragment<WishCitiesFragment.Listener>(),
    WishCitiesView, SearchCityFragment.Caller {

    interface Listener : NavigationController

    companion object {
        const val REQUEST_CITY = 0
    }

    private lateinit var itemTouchHelper: ItemTouchHelper
    private var undoSnackbar: Snackbar? = null
    private lateinit var liftOnScrollListener: LiftOnScrollListener
    private lateinit var presenter: WishCitiesPresenter
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val schedulerProvider = MainSchedulerProvider.INSTANCE
        val repository = DataRepositoryFactory.getWishCitiesRepository()
        val messageDispatcher =
            RxDelayedMessageDispatcher(getRemoveDuration(), schedulerProvider)
        presenter =
            WishCitiesPresenter(schedulerProvider, repository, messageDispatcher)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmet_wish_list, container, false)
    }


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
                        Screen.Factory.searchCity(this@WishCitiesFragment, REQUEST_CITY)
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
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
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
        wishViewSwitcher.displayedChild = 0
    }

    override fun showList() {
        wishViewSwitcher.displayedChild = 1
    }

    override fun showMessageEmpty() {
        wishViewSwitcher.displayedChild = 2
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
        parentListener?.navigateTo(Screen.Factory.pager(position))
    }

    private fun getRemoveDuration(): Long {
        return resources.getInteger(R.integer.wish_remove_delay).toLong()
    }

    override fun onCityInserted(position: Int) {
        presenter.onCityInsertedAt(position)
    }

}
