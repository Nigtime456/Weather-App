/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.search.paging

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.common.utility.ColorSpanHelper
import com.nigtime.weatherapplication.domain.location.SearchCity
import kotlinx.android.synthetic.main.item_search_city.view.*

class SearchCityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentCity: SearchCity? = null

    fun bind(city: SearchCity, spannHelper: ColorSpanHelper) {
        currentCity = city
        itemView.itemSearchName.text = spannHelper.highlightText(city.name, city.query)
        itemView.itemSearchDescription.text = city.getStateAndCounty()
        //уже добавлен
        itemView.isEnabled = !city.isSaved
    }

    fun recycle() {
        currentCity = null
    }

    fun getCurrentCity(): SearchCity {
        return currentCity ?: error("currentCity == null")
    }
}
