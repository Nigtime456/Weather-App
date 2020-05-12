/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.ColorSpanHelper
import com.nigtime.weatherapplication.common.utility.SimpleTextWatcher
import com.nigtime.weatherapplication.common.utility.ThemeHelper
import com.nigtime.weatherapplication.common.utility.list.ColorDividerDecoration
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterFactory
import com.nigtime.weatherapplication.screen.search.paging.PagedSearchAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_search_city.*


class SearchCityFragment :
    BaseFragment<SearchCityView, SearchCityPresenter, NavigationController>(R.layout.fragment_search_city),
    SearchCityView {

    companion object {
        private val insertSubject: Subject<Int> = PublishSubject.create()

        /**
         * [Observable] который вернет позицию нового гороад,
         * если он добавлен.
         * Другие фрагменты могут использовать это для координации
         * списка/страниц.
         */
        fun observeInsert(): Observable<Int> = insertSubject
    }

    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        val scrollOffset = searchRecycler.computeVerticalScrollOffset()
        liftAppBar(scrollOffset > 0)
    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterFactory(): PresenterFactory<SearchCityPresenter> {
        return ViewModelProvider(this).get(SearchCityPresenterFactory::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.onViewReady()
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
        setupLocationButton()
        setupAppBar()
    }

    private fun setupAppBar() {
        searchToolbar.setNavigationOnClickListener {
            presenter.onClickNavigationButton()
        }
    }

    private fun setupLocationButton() {
        searchCurrentLocation.setOnClickListener {
            showToast("TODO")
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

    override fun setInsertedResult(position: Int) {
        insertSubject.onNext(position)
    }

    override fun navigateToPreviousScreen() {
        parentListener?.toBack()
    }

    private fun EditText.changeTextListener(block: (String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                block(s.toString())
            }
        })
    }
}
