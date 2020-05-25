/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.screen.search.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.util.ColorSpanHelper
import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity

/**
 * Адаптер для подгружаемого списка
 */
class PagedSearchAdapter constructor(private val spannHelper: ColorSpanHelper) :
    PagedListAdapter<SearchCity, SearchCityViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchCity>() {
            override fun areItemsTheSame(
                oldItem: SearchCity,
                newItem: SearchCity
            ): Boolean {
                return oldItem.cityId == newItem.cityId
            }

            override fun areContentsTheSame(
                oldItem: SearchCity,
                newItem: SearchCity
            ): Boolean {
                return oldItem.cityId == newItem.cityId && oldItem.query == newItem.query
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchCityViewHolder(inflater.inflate(R.layout.item_search_city, parent, false))
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        val searchCityData = getItem(position)
        searchCityData?.let { item -> holder.bind(item, spannHelper) }
    }

    override fun onViewRecycled(holder: SearchCityViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }
}