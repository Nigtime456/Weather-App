/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.search

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.helper.ColorSpanHelper
import com.nigtime.weatherapplication.common.helper.ThemeHelper
import com.nigtime.weatherapplication.common.helper.ToastController
import com.nigtime.weatherapplication.common.helper.list.ColorDividerDecoration
import com.nigtime.weatherapplication.common.helper.list.LiftOnScrollListener
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screens.common.BaseFragment
import com.nigtime.weatherapplication.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.screens.common.NavigationController
import com.nigtime.weatherapplication.screens.search.paging.PagedSearchAdapter
import kotlinx.android.synthetic.main.fragment_search_city.*

/**
 * TODO краши
 * Из за анимации краши.
 * Исправить анимацию: удалить анимацию ViewAnimator, написать свою crossfade.
 * Если не поможет поменять ViewAnimator на FrameLayout
 */
class SearchCityFragment : BaseFragment<SearchCityFragment.Listener>(R.layout.fragment_search_city),
    SearchCityView {

    interface Listener : NavigationController

    interface TargetFragment {
        fun onCityInserted(position: Int)
    }

    companion object {
        //TODO констант
        const val EXTRA_INSERTED_POSITION =
            "com.nigtime.weatherapplication.search.inserted_position"

        fun newInstance(): SearchCityFragment = SearchCityFragment()
    }


    private lateinit var toastController: ToastController
    private lateinit var liftOnScrollListener: LiftOnScrollListener
    private var presenter: SearchCityPresenter =
        App.INSTANCE.appContainer.searchCityContainer.searchCityPresenter

    override fun provideListenerClass(): Class<Listener>? = Listener::class.java


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
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themePrimaryDividerColor)
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
