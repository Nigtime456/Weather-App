/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.ui.screens.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.CityForForecastData
import com.nigtime.weatherapplication.db.repository.SelectedCitySourceImpl
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import kotlinx.android.synthetic.main.fragment_city_pager.*


class CityPagerFragment :
    BaseFragment<CityPagerView, CityPagerPresenter, CityPagerFragment.Listener>(), CityPagerView {

    interface Listener : NavigationController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_pager, container, false)
    }

    override fun provideMvpPresenter(): CityPagerPresenter {
        val geoCityDao = AppDatabase.Instance.get(requireContext()).geoCityDao()
        val selectedCityDao = AppDatabase.Instance.get(requireContext()).selectedCityDao()
        val citySource = SelectedCitySourceImpl(geoCityDao, selectedCityDao)
        return CityPagerPresenter(MainSchedulerProvider.INSTANCE, citySource)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        configureViewPager()
        presenter.provideCities()
    }

    private fun configureViewPager() {
        fragmentCityPagerViewPager.adapter = CityPagerAdapter(this)
    }

    override fun submitList(items: List<CityForForecastData>) {
        (fragmentCityPagerViewPager.adapter as CityPagerAdapter).submitList(items)
    }
}
