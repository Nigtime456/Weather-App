/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.screen.pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.PresenterFactory
import com.nigtime.weatherapplication.screen.common.Screen
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.wishlist.WishCitiesFragment
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_pager.*


class PagerCityFragment :
    BaseFragment<PagerCityView, PagerCityPresenter, NavigationController>(R.layout.fragment_pager),
    PagerCityView,
    CurrentForecastFragment.ParentListener{


    companion object {
        private const val PAGE_LIMIT = 1

    }

    private val pagerScrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            presenter.onPageScrolled(position)
        }

    }

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterFactory(): PresenterFactory<PagerCityPresenter> {
        return ViewModelProvider(this).get(PagerCityPresenterFactory::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.provideCities()
    }


    override fun onStop() {
        super.onStop()
        presenter.setPagerPosition(pagerViewPager.currentItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerViewPager.unregisterOnPageChangeCallback(pagerScrollListener)
        pagerViewPager.adapter = null
    }

    private fun initViews() {
        setupNavView()
        setupViewPager()
    }

    private fun setupNavView() {
        pagerNavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAboutApp -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                R.id.menuChangeCityList -> {
                    presenter.onClickChangeCityList()
                }
                R.id.menuSettings -> {
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_LONG).show()
                }
                else -> {
                    presenter.onClickNavigationItem(menuItem.itemId)
                }
            }
            closeDrawer()
            true
        }
    }

    @SuppressLint("WrongConstant")
    private fun setupViewPager() {
        pagerViewPager.apply {
            offscreenPageLimit = PAGE_LIMIT
            registerOnPageChangeCallback(pagerScrollListener)
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.divider_size)))
        }
    }

    private fun closeDrawer() {
        pagerDrawer.closeDrawer(GravityCompat.START)
    }

    private fun openDrawer() {
        pagerDrawer.openDrawer(GravityCompat.START)
    }

    override fun submitPageList(items: List<CityForForecast>) {
        pagerViewPager.adapter = PagerCityAdapter(this, items)
    }

    override fun submitNavigationList(items: List<Pair<Int, String>>) {
        items.forEach { pair ->
            val menuItem = pagerNavView.menu
                .getItem(0)
                .subMenu
                .add(0, pair.first, 0, pair.second)
            menuItem.isCheckable = true
            menuItem.setIcon(R.drawable.ic_location_city)
        }
    }

    override fun setCurrentPage(page: Int, smoothScroll: Boolean) {
        pagerViewPager.setCurrentItem(page, smoothScroll)
    }

    override fun selectNavigationItem(index: Int) {
        pagerNavView.setCheckedItem(index)
    }

    override fun onClickAddCity() {
        parentListener?.navigateTo(Screen.Factory.searchCity())
    }

    override fun navigateToWishListScreen() {
        parentListener?.navigateTo(Screen.Factory.wishList())
    }

    override fun onClickOpenDrawer() {
        openDrawer()
    }


    //TODO исправь
     fun onCityInserted(position: Int) {
        //TODO в этот момент презентер отсоеден
        presenter.setPagerPosition(position)
    }

    //TODO исправь
    fun onSelectCity(position: Int) {
        //TODO в этот момент презентер отсоеден
        presenter.setPagerPosition(position)
    }
}
