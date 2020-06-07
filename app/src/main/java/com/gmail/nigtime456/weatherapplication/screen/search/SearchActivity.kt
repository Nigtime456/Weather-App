/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.location.SearchCity
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.base.BaseActivity
import com.gmail.nigtime456.weatherapplication.screen.search.di.DaggerSearchComponent
import com.gmail.nigtime456.weatherapplication.screen.search.di.SearchModule
import com.gmail.nigtime456.weatherapplication.screen.search.list.SearchCityModel
import com.gmail.nigtime456.weatherapplication.tools.ui.changeTextListener
import com.gmail.nigtime456.weatherapplication.tools.ui.getColorFromAttr
import com.gmail.nigtime456.weatherapplication.tools.ui.getDimension
import com.gmail.nigtime456.weatherapplication.tools.ui.showSnackBar
import com.gmail.nigtime456.weatherapplication.ui.list.ColorDividerDecoration
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity(),
    SearchContract.View,
    FlexibleAdapter.OnItemClickListener,
    FlexibleAdapter.OnUpdateListener {

    companion object {
        const val EXTRA_INSERTED_POSITION = "weatherapplication.screen.search.inserted_position"

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
            .appComponent(appComponent)
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
        setSupportActionBar(searchToolbar)
    }

    private fun initList() {
        searchAdapter = FlexibleAdapter(null, this)

        searchRecycler.apply {
            itemAnimator = null
            adapter = searchAdapter
            addItemDecoration(getDivider())
            searchAppbar.setTarget(this)
            setHasFixedSize(true)
        }
    }

    private fun initInputField() {
        searchEditText.changeTextListener(presenter::queryChanges)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = getColorFromAttr(R.attr.colorControlHighlight)
        val dividerSize = getDimension(R.dimen.divider_size)
        return ColorDividerDecoration(dividerColor, dividerSize)
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
        searchAppbar.setLift(false)
        searchViewSwitcher.switchTo(0, true)
    }

    override fun showListLayout() {
        searchViewSwitcher.switchTo(1, false)
    }

    override fun showEmptyLayout() {
        searchAppbar.setLift(false)
        searchViewSwitcher.switchTo(3, true)
    }

    override fun showNotFoundLayout() {
        searchAppbar.setLift(false)
        searchViewSwitcher.switchTo(2, false)
    }

    override fun showMessageAlreadySaved() {
        searchRootView.showSnackBar(R.string.search_already_selected)
    }

    override fun showSearchResult(items: List<SearchCity>) {
        searchAdapter.updateDataSet(SearchCityModel.mapList(items), true)
    }

    override fun scrollToPosition(position: Int) {
        searchRecycler.scrollToPosition(position)
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
