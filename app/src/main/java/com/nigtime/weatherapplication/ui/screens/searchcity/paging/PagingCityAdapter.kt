/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.ui.screens.searchcity.paging

import android.view.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.ui.tools.ColorSpanHelper
import kotlinx.android.synthetic.main.item_search_list.view.*

/**
 * Адаптер для подгружаемого списка
 */
class PagingCityAdapter constructor(private val spannHelper: ColorSpanHelper) :
    PagedListAdapter<SearchCityData, PagingCityAdapter.CityViewHolder>(DIFF_CALLBACK) {

    /**
     * Оптимизированная обработка кликов: не создается OnClickListener на каждый элемент
     */
    class ItemClickClickListener constructor(
        private val recyclerView: RecyclerView,
        private val onClick: (SearchCityData) -> Unit
    ) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        private val simpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }
        }

        init {
            recyclerView.addOnItemTouchListener(this)
            gestureDetector = GestureDetector(recyclerView.context, simpleOnGestureListener)
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            child?.let {
                if (gestureDetector.onTouchEvent(e)) {
                    val viewHolder = recyclerView.getChildViewHolder(child)
                    val cityViewHolder = viewHolder as CityViewHolder
                    onClick(cityViewHolder.getCurrentCity())
                }
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            //nothing
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            //nothing
        }
    }


    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentCity: SearchCityData? = null

        fun bind(city: SearchCityData, spannHelper: ColorSpanHelper) {
            currentCity = city
            itemView.itemSearchCityName.text = spannHelper.highlightText(city.name, city.query)
            itemView.itemSearchStateAndCountry.text = getStateAndCounty(city)
            //это используется для выделения добавленных городов
            itemView.isActivated = city.isSelected
        }

        fun onRecycled() {
            currentCity = null
        }

        fun getCurrentCity(): SearchCityData {
            return currentCity ?: error("currentCity == null")
        }


        private fun getStateAndCounty(searchCityData: SearchCityData): CharSequence? {
            return if (searchCityData.hasState()) {
                if (searchCityData.hasCountry()) {
                    "%s, %s".format(searchCityData.countryName, searchCityData.stateName)
                } else {
                    searchCityData.stateName
                }
            } else {
                searchCityData.countryName
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchCityData>() {
            override fun areItemsTheSame(
                oldItem: SearchCityData,
                newItem: SearchCityData
            ): Boolean {
                return oldItem.cityId == newItem.cityId
            }

            override fun areContentsTheSame(
                oldItem: SearchCityData,
                newItem: SearchCityData
            ): Boolean {
                return oldItem.cityId == newItem.cityId && oldItem.query == newItem.query
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CityViewHolder(inflater.inflate(R.layout.item_search_list, parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val searchCityData = getItem(position)
        searchCityData?.let { holder.bind(it, spannHelper) }
    }

    override fun onViewRecycled(holder: CityViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

}