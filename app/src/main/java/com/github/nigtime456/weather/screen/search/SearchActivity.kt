/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import com.github.nigtime456.weather.App
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.location.SearchCity
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.base.BaseActivity
import com.github.nigtime456.weather.screen.search.di.DaggerSearchComponent
import com.github.nigtime456.weather.screen.search.di.SearchModule
import com.github.nigtime456.weather.screen.search.list.SearchCityModel
import com.github.nigtime456.weather.ui.list.HorizontalDividerDecoration
import com.github.nigtime456.weather.utils.ui.*
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity(),
    SearchContract.View,
    FlexibleAdapter.OnItemClickListener,
    FlexibleAdapter.OnUpdateListener,
    SearchView.OnQueryTextListener {

    companion object {
        const val EXTRA_INSERTED_POSITION = "weatherapplication.search.inserted_position"

        fun getIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    //<editor-fold desc = "fields">

    @Inject
    lateinit var presenter: SearchContract.Presenter

    private lateinit var searchAdapter: FlexibleAdapter<SearchCityModel>

    //</editor-fold>

    //<editor-fold desc = "lifecycle">

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setResult(Activity.RESULT_CANCELED)
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    //</editor-fold>

    //<editor-fold desc = "initialization">
    override fun initDi(appComponent: AppComponent) {
        DaggerSearchComponent.builder()
            .appComponent(App.getComponent())
            .searchModule(SearchModule(this))
            .build()
            .inject(this)
    }

    private fun initViews() {
        initAppBar()
        initList()
        initInputField()
    }

    private fun initAppBar() {
        activity_search_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initList() {
        searchAdapter = FlexibleAdapter(null, this)
        activity_search_recycler.apply {
            itemAnimator = null
            adapter = searchAdapter
            recycledViewPool.setMaxRecycledViews(SearchCityModel.VIEW_TYPE, 10)
            addItemDecoration(getDivider())
            setHasFixedSize(true)
        }
    }

    private fun initInputField() {
        activity_search_text_field.apply {
            setMaxLength(getInteger(R.integer.field_max_length))
            setOnQueryTextListener(this@SearchActivity)
        }
    }

    private fun getDivider(): HorizontalDividerDecoration {
        val dividerColor = getColorFromAttr(R.attr.dividerColor)
        val dividerSize = getDimension(R.dimen.divider_size)
        return HorizontalDividerDecoration(dividerColor, dividerSize)
    }

    //</editor-fold>

    //<editor-fold desc = "ui events">

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        presenter.queryChanges(newText)
        return false
    }

    //</editor-fold>

    //<editor-fold desc = "adapter events">

    override fun onItemClick(view: View, position: Int): Boolean {
        searchAdapter.getItem(position)?.let {
            presenter.clickItem(it.city)
        }
        return false
    }

    override fun onUpdateEmptyView(size: Int) {
        presenter.onListUpdated()
    }

    //</editor-fold>

    //<editor-fold desc = "MVP">

    override fun showProgressLayout() {
        activity_search_view_switcher.switchTo(0, true)
    }

    override fun showListLayout() {
        activity_search_view_switcher.switchTo(1, false)
    }

    override fun showEmptyLayout() {
        activity_search_view_switcher.switchTo(3, true)
    }

    override fun showNotFoundLayout() {
        activity_search_view_switcher.switchTo(2, false)
    }

    override fun showMessageAlreadySaved() {
        activity_search_root_view.showSnackBar(R.string.activity_search_already_saved)
    }

    override fun showSearchResult(items: List<SearchCity>) {
        searchAdapter.updateDataSet(SearchCityModel.mapList(items), true)
    }

    override fun scrollToPosition(position: Int) {
        activity_search_recycler.scrollToPosition(position)
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

    //</editor-fold>

}
