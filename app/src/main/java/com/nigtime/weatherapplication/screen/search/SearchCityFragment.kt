/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.util.ColorSpanHelper
import com.nigtime.weatherapplication.common.util.SimpleTextWatcher
import com.nigtime.weatherapplication.common.util.ThemeUtils
import com.nigtime.weatherapplication.common.util.list.ColorDividerDecoration
import com.nigtime.weatherapplication.domain.location.SearchCity
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.search.paging.PagedListItemClickListener
import com.nigtime.weatherapplication.screen.search.paging.PagedSearchAdapter
import kotlinx.android.synthetic.main.fragment_search_city.*


class SearchCityFragment :
    BaseFragment<SearchCityView, SearchCityPresenter, NavigationController>(R.layout.fragment_search_city),
    SearchCityView {

    interface TargetFragment {
        fun onCityInserted(position: Int)
    }

    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        val scrollOffset = searchRecycler.computeVerticalScrollOffset()
        liftAppBar(scrollOffset > 0)
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): BasePresenterProvider<SearchCityPresenter> {
        return ViewModelProvider(this).get(SearchCityPresenterProvider::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        searchRecycler.viewTreeObserver.addOnScrollChangedListener(liftScrollListener)
    }

    override fun onStop() {
        super.onStop()
        searchRecycler.viewTreeObserver.removeOnScrollChangedListener(liftScrollListener)
    }

    private fun initViews() {
        setupRecycler()
        setupInputField()
        setupAppBar()
    }

    private fun setupAppBar() {
        searchToolbar.setNavigationOnClickListener {
            presenter.onNavigationButtonClick()
        }
    }

    private fun setupInputField() {
        searchEditText.changeTextListener(presenter::processTextInput)
    }

    private fun setupRecycler() {
        searchRecycler.apply {
            adapter = PagedSearchAdapter(getSpanHelper())
            addItemDecoration(getDivider())
            itemAnimator = null
            PagedListItemClickListener(this, presenter::onItemClick)
        }

    }

    private fun liftAppBar(lift: Boolean) {
        searchAppbar.isSelected = lift
    }

    private fun getSpanHelper(): ColorSpanHelper {
        val highlightColor = ThemeUtils.getAttrColor(requireContext(), R.attr.colorAccent)
        return ColorSpanHelper(highlightColor)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeUtils.getAttrColor(requireContext(), R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(dividerColor, dividerSize)
    }

    override fun showHintLayout() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(0, true)
    }

    override fun showProgressLayout() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(1, true)
    }

    override fun showListLayout() {
        searchViewSwitcher.switchTo(2, false)
    }

    override fun showEmptyLayout() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(3, false)
    }

    override fun showToastAlreadyAdded() {
        showToast(R.string.search_already_selected)
    }

    override fun hideSoftKeyboard() {
        ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            ?.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun submitList(pagedList: PagedList<SearchCity>) {
        (searchRecycler.adapter as PagedSearchAdapter).submitList(pagedList)
    }

    override fun delayScrollToPosition(position: Int) {
        //Т.к. из за DiffUtil список формирмуется с задержкой
        searchRecycler.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                searchRecycler.scrollToPosition(position)
                searchRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    override fun setInsertionResult(position: Int) {
        if (targetFragment is TargetFragment) {
            (targetFragment as TargetFragment).onCityInserted(position)
        }
    }

    override fun navigateToPreviousScreen() {
        attachedListener?.toPreviousScreen()
    }


    private fun EditText.changeTextListener(block: (String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                block(s.toString())
            }
        })
    }

}
