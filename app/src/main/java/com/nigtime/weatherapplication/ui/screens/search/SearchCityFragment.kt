/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.cities.SearchCity
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.search.paging.PagedListLoaderImpl
import com.nigtime.weatherapplication.ui.screens.search.paging.PagedSearchAdapter
import com.nigtime.weatherapplication.ui.helpers.ColorSpanHelper
import com.nigtime.weatherapplication.ui.helpers.ThemeHelper
import com.nigtime.weatherapplication.ui.helpers.ToastController
import com.nigtime.weatherapplication.ui.helpers.list.ColorDividerDecoration
import com.nigtime.weatherapplication.ui.helpers.list.LiftOnScrollListener
import com.nigtime.weatherapplication.utility.di.CitiesRepositoryFactory
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import kotlinx.android.synthetic.main.fragment_search_city.*

/**
 * TODO краши
 * Из за анимации краши.
 * Исправить анимацию: удалить анимацию ViewAnimator, написать свою crossfade.
 * Если не поможет поменять ViewAnimator на FrameLayout
 */
class SearchCityFragment : BaseFragment<SearchCityFragment.Listener>(), SearchCityView {

    interface Listener : NavigationController

    interface TargetFragment {
        fun onCityInserted(position: Int)
    }

    private lateinit var toastController: ToastController
    private lateinit var liftOnScrollListener: LiftOnScrollListener
    private lateinit var presenter: SearchCityPresenter

    companion object {
        const val EXTRA_INSERTED_POSITION =
            "com.nigtime.weatherapplication.search.inserted_position"
    }

    override fun provideListenerClass(): Class<Listener>? = Listener::class.java


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val schedulerProvider = MainSchedulerProvider.INSTANCE
        val pagedListLoader = PagedListLoaderImpl(schedulerProvider)
        val pagedSearchRepository = CitiesRepositoryFactory.getPagedSearchRepository()
        presenter = SearchCityPresenter(schedulerProvider, pagedSearchRepository, pagedListLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liftOnScrollListener.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        toastController =
            ToastController(
                requireContext()
            )
        presenter.onViewCreated()
        initViews()
    }

    private fun initViews() {
        initRecycler()

        searchEditText.simpleTextListener {
            presenter.processInput(it)
        }

        searchCurrentLocation.setOnClickListener {
            toastController.showToast("TODO")
        }

        searchToolbar.setNavigationOnClickListener {
            presenter.onClickNavigationButton()
        }
    }

    private fun initRecycler() {
        searchRecycler.apply {
            adapter = PagedSearchAdapter(getSpanHelper())
            addItemDecoration(getDivider())
            itemAnimator = null
            PagedSearchAdapter.ItemClickClickListener(this) { searchCityData ->
                presenter.onClickItem(searchCityData)
            }
            setLiftOnScrollAppBar(this)
        }
    }

    private fun setLiftOnScrollAppBar(recyclerView: RecyclerView) {
        liftOnScrollListener = LiftOnScrollListener(recyclerView, this::liftAppBar)
    }

    private fun liftAppBar(lift: Boolean) {
        searchAppbar.isSelected = lift
    }

    private fun getSpanHelper(): ColorSpanHelper {
        val highlightColor = ThemeHelper.getColor(requireContext(), R.attr.colorAccent)
        return ColorSpanHelper(
            highlightColor
        )
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }


    override fun submitList(pagedList: PagedList<SearchCity>) {
        searchRecycler.apply {
            (adapter as PagedSearchAdapter).submitList(pagedList)
        }
    }

    override fun delayScrollListToPosition(position: Int) {
        //Т.к. из за DiffUtil список формирмует с задержкой
        searchRecycler.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                searchRecycler.scrollToPosition(position)
                searchRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    override fun showHint() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(0, true)
    }

    override fun showProgressBar() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(1, true)
    }

    override fun showList() {
        searchViewSwitcher.switchTo(2, false)
    }

    override fun showMessageEmpty() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(3, false)
    }

    override fun showMessageAlreadyWish() {
        toastController.showToast(R.string.search_already_selected)
    }

    override fun setInsertedResultOk(position: Int) {
        if (targetFragment is TargetFragment) {
            (targetFragment as TargetFragment).onCityInserted(position)
        }
    }

    override fun setInsertedResultCanceled() {
        targetFragment?.let {
            onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
        }
    }

    override fun navigateToPreviousScreen() {
        parentListener?.toBack()
    }

    private fun EditText.simpleTextListener(block: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                block(s.toString())
            }
        })
    }

}
