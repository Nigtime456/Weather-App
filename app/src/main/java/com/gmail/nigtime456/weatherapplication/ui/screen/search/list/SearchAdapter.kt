/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.BaseAdapter
import com.gmail.nigtime456.weatherapplication.ui.list.SimpleDiffCallback
import com.gmail.nigtime456.weatherapplication.ui.util.ColorSpanHelper

class SearchAdapter(
    rxAsyncDiffer: RxAsyncDiffer,
    private val spanHelper: ColorSpanHelper,
    private val onClick: (SearchCity) -> Unit
) : BaseAdapter<SearchCity, SearchCityViewHolder>(DIFF_CALLBACK, rxAsyncDiffer) {

    private companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<SearchCity>() {
            override fun areItemsTheSame(old: SearchCity, new: SearchCity): Boolean {
                return old.cityId == new.cityId
            }

            override fun areContentsTheSame(old: SearchCity, new: SearchCity): Boolean {
                return old.cityId == new.cityId
            }

        }
    }

    override fun onViewAttachedToWindow(holder: SearchCityViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            onClick(getItem(holder.adapterPosition))
        }
    }

    override fun onViewDetachedFromWindow(holder: SearchCityViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    override fun bindViewHolder(holder: SearchCityViewHolder, position: Int, item: SearchCity) {
        holder.bind(item, spanHelper)
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): SearchCityViewHolder {
        val view = inflater.inflate(R.layout.item_search_city, parent,false)
        return SearchCityViewHolder(view)
    }
}

