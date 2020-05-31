/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewTreeObserver
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.ColorDividerDecoration
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.ui.screen.search.di.DaggerSearchComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.search.di.SearchModule
import com.gmail.nigtime456.weatherapplication.ui.screen.search.list.SearchAdapter
import com.gmail.nigtime456.weatherapplication.ui.util.ColorSpanHelper
import com.gmail.nigtime456.weatherapplication.ui.util.ThemeUtils
import com.gmail.nigtime456.weatherapplication.ui.util.changeTextListener
import com.gmail.nigtime456.weatherapplication.ui.util.showSnackBar
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity(), SearchContract.View {

    companion object {
        const val EXTRA_INSERTED_POSITION = "weatherapplication.screen.search.inserted_position"

        fun getIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    @Inject
    lateinit var presenter: SearchContract.Presenter

    @Inject
    lateinit var rxAsyncDiffer: RxAsyncDiffer

    private val searchAdapter by lazy {
        SearchAdapter(
            rxAsyncDiffer,
            getSpanHelper(),
            presenter::clickItem
        )
    }

    private val liftScrollListener = ViewTreeObserver.OnScrollChangedListener {
        val scrollOffset = searchRecycler.computeVerticalScrollOffset()
        liftAppBar(scrollOffset > 0)
    }

    private fun getSpanHelper(): ColorSpanHelper {
        val highlightColor = ThemeUtils.getAttrColor(this, R.attr.colorAccent)
        return ColorSpanHelper(highlightColor)
    }

    private fun liftAppBar(lift: Boolean) {
        searchAppbar.isSelected = lift
    }

    override fun initDi(appComponent: AppComponent) {
        DaggerSearchComponent.builder()
            .appComponent(appComponent)
            .searchModule(SearchModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setResult(Activity.RESULT_CANCELED)
        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    private fun initViews() {
        initAppBar()
        initList()
        initInputField()
    }

    private fun initAppBar() {
        setSupportActionBar(searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initList() {
        searchRecycler.apply {
            itemAnimator = null
            adapter = searchAdapter
            addItemDecoration(getDivider())
            viewTreeObserver.addOnScrollChangedListener(liftScrollListener)
        }
    }

    private fun initInputField() {
        searchEditText.changeTextListener(presenter::queryChanges)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeUtils.getAttrColor(this, R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(dividerColor, dividerSize)
    }

    override fun showProgressLayout() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(0, true)
    }

    override fun showListLayout() {
        searchViewSwitcher.switchTo(1, false)
    }

    override fun showEmptyLayout() {
        searchViewSwitcher.switchTo(3, true)
    }

    override fun showNotFoundLayout() {
        liftAppBar(false)
        searchViewSwitcher.switchTo(2, true)
    }

    override fun showMessageAlreadySaved() {
        searchRootView.showSnackBar(R.string.search_already_selected)
    }

    override fun showSearchResult(list: List<SearchCity>, scrollToPosition: Int) {
        searchAdapter.submitList(list) {
            searchRecycler.scrollToPosition(scrollToPosition)
        }
    }

    override fun setInsertResult(position: Int) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(EXTRA_INSERTED_POSITION, position)
        )
    }

    override fun finishView() {
        finish()
    }
}
