/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.screens.listcities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.SelectedCityData
import com.nigtime.weatherapplication.db.repository.SelectedCitySourceImpl
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.tools.ThemeHelper
import com.nigtime.weatherapplication.ui.tools.list.ColorDividerDecoration
import com.nigtime.weatherapplication.ui.tools.list.LiftOnScrollListener
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.utility.rx.RxDelayedMessageDispatcher
import kotlinx.android.synthetic.main.fragmet_list_cities.*


class ListCitiesFragment :
    BaseFragment<ListCitiesView, ListCitiesPresenter, ListCitiesFragment.Listener>(),
    ListCitiesView {

    interface Listener : NavigationController

    private lateinit var itemTouchHelper: ItemTouchHelper
    private var undoSnackbar: Snackbar? = null
    private lateinit var liftOnScrollListener: LiftOnScrollListener

    private val adapterListener = object : ListCitiesAdapter.Listener {
        override fun onRequestDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }

        override fun onItemSwiped(
            item: SelectedCityData,
            position: Int,
            items: MutableList<SelectedCityData>
        ) {
            presenter.onItemSwiped(item, position, items)
        }

        override fun onItemsMoved(items: List<SelectedCityData>) {
            presenter.onItemsMoved(items)
        }


    }

    override fun provideListenerClass(): Class<Listener>? = Listener::class.java

    override fun provideMvpPresenter(): ListCitiesPresenter {
        val geoCityDao = AppDatabase.Instance.get(requireContext()).geoCityDao()
        val selectedCityDao = AppDatabase.Instance.get(requireContext()).selectedCityDao()
        val selectedCitySource = SelectedCitySourceImpl(geoCityDao, selectedCityDao)
        val delayedMessageDispatcher =
            RxDelayedMessageDispatcher(getRemoveDuration(), MainSchedulerProvider.INSTANCE)

        return ListCitiesPresenter(
            MainSchedulerProvider.INSTANCE,
            selectedCitySource,
            delayedMessageDispatcher
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmet_list_cities, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liftOnScrollListener.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureRecycler()
        configureAppBar()
        presenter.provideCities()
    }

    override fun onStop() {
        super.onStop()
        presenter.onFragmentStop()
    }

    private fun configureAppBar() {
        fragmentListCitiesToolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.menuAddCity) {
                listener?.navigateTo(Screen.SEARCH_CITY)
            }
            true
        }
    }

    private fun configureRecycler() {
        fragmentListCitiesRecycler.apply {
            val citiesAdapter = ListCitiesAdapter(adapterListener)
            adapter = citiesAdapter
            initTouchGestures(citiesAdapter)
            addItemDecoration(getDivider())
            itemTouchHelper.attachToRecyclerView(this)
            setLiftOnScrollAppBar(this)
        }
    }

    private fun setLiftOnScrollAppBar(recyclerView: RecyclerView) {
        liftOnScrollListener = LiftOnScrollListener(recyclerView) { doLift ->
            fragmentListCitiesAppbar.isSelected = doLift
        }
    }

    private fun initTouchGestures(citiesAdapter: ListCitiesAdapter) {
        val itemTouchController = ItemTouchController(citiesAdapter)
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

    override fun submitList(list: List<SelectedCityData>) {
        (fragmentListCitiesRecycler.adapter as ListCitiesAdapter).submitList(list)
    }

    override fun showProgressBar() {
        fragmentListCitiesViewSwitcher.displayedChild = 0
    }

    override fun showList() {
        fragmentListCitiesViewSwitcher.displayedChild = 1
    }

    override fun showMessageEmpty() {
        fragmentListCitiesViewSwitcher.displayedChild = 2
    }


    override fun insertItemToList(item: SelectedCityData, position: Int) {
        fragmentListCitiesRecycler.smoothScrollToPosition(position)
        (fragmentListCitiesRecycler.adapter as ListCitiesAdapter).insertItemToList(item, position)
    }

    override fun showUndoDeleteSnack() {
        undoSnackbar =
            Snackbar.make(
                fragmentListCitiesRoot,
                R.string.fragment_list_city_removed,
                getRemoveDuration().toInt()
            ).apply {
                setAction(R.string.fragment_list_city_undo) { presenter.onClickUndoDelete() }
                show()
            }
    }

    override fun hideUndoDeleteSnack() {
        undoSnackbar?.dismiss()
        undoSnackbar = null
    }

    private fun getRemoveDuration(): Long {
        return resources.getInteger(R.integer.list_cities_remove_delay).toLong()
    }

}
