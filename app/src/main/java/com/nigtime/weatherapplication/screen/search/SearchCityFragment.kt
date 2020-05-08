/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.helper.ColorSpanHelper
import com.nigtime.weatherapplication.common.helper.ThemeHelper
import com.nigtime.weatherapplication.common.helper.list.ColorDividerDecoration
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import com.nigtime.weatherapplication.screen.search.paging.PagedSearchAdapter
import kotlinx.android.synthetic.main.fragment_search_city.*


class SearchCityFragment :
    BaseFragment<SearchCityView, SearchCityPresenter, SearchCityFragment.ParentListener>(R.layout.fragment_search_city),
    SearchCityView {

    interface ParentListener : NavigationController

    /**
     * Интерфейс слушателя, который получит позицию добавленого города.
     */
    interface TargetFragment {
        fun onCityInserted(position: Int)
    }

    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        val scrollOffset = searchRecycler.computeVerticalScrollOffset()
        liftAppBar(scrollOffset > 0)
    }

    override fun provideListenerClass(): Class<ParentListener>? = ParentListener::class.java

    override fun getPresenterHolder(): PresenterProvider<SearchCityPresenter> {
        return ViewModelProvider(this).get(SearchCityViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.onViewCreated()
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

        searchEditText.changeTextListener(presenter::processInput)

        searchCurrentLocation.setOnClickListener {
            showToast("TODO")
        }

        searchToolbar.setNavigationOnClickListener {
            presenter.onClickNavigationButton()
        }
    }

    private fun setupRecycler() {
        searchRecycler.apply {
            adapter = PagedSearchAdapter(getSpanHelper())
            addItemDecoration(getDivider())
            itemAnimator = null
            PagedSearchAdapter.ItemClickClickListener(this, presenter::onClickItem)
        }
    }

    private fun liftAppBar(lift: Boolean) {
        searchAppbar.isSelected = lift
    }

    private fun getSpanHelper(): ColorSpanHelper {
        val highlightColor = ThemeHelper.getColor(requireContext(), R.attr.colorAccent)
        return ColorSpanHelper(highlightColor)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(dividerColor, dividerSize)
    }


    override fun submitList(pagedList: PagedList<SearchCity>) {
        (searchRecycler.adapter as PagedSearchAdapter).submitList(pagedList)
    }

    override fun delayScrollListToPosition(position: Int) {
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
        showToast(R.string.search_already_selected)
    }

    override fun setInsertedResult(position: Int) {
        if (targetFragment is TargetFragment) {
            (targetFragment as TargetFragment).onCityInserted(position)
        }
    }

    override fun navigateToPreviousScreen() {
        parentListener?.toBack()
    }

    private fun EditText.changeTextListener(block: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                block(s.toString())
            }
        })
    }


}
