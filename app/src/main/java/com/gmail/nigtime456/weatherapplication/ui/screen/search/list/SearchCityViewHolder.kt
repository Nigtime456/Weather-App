/*
 * Сreated by Igor Pokrovsky. 2020/5/31
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.ui.util.ColorSpanHelper
import kotlinx.android.synthetic.main.item_search_city.view.*

class SearchCityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(city: SearchCity, spanHelper: ColorSpanHelper) {
        itemView.itemSearchName.text = spanHelper.highlightText(city.name, city.query)
        itemView.itemSearchDescription.text = city.getStateAndCounty()
        //если уже добавлен - изменим цвет текста
        itemView.itemSearchTextLayout.isEnabled = !city.isSaved
    }
}