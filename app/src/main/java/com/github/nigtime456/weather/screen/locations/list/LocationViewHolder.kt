/*
 * Сreated by Igor Pokrovsky. 2020/6/2
 */


package com.github.nigtime456.weather.screen.locations.list

import android.view.View
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.data.weather.WeatherUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*

class LocationViewHolder(
    override val containerView: View,
    adapter: FlexibleAdapter<out IFlexible<*>>
) : FlexibleViewHolder(containerView, adapter),
    LocationViewHolderContract.View,
    LayoutContainer {

    var presenter: LocationViewHolderContract.Presenter? = null

    init {
        setDragHandleView(item_location_drag_handle)
        item_location_layout.setOnClickListener(this)
        item_location_remove.setOnClickListener(this)
    }

    fun attachPresenter() {
        presenter?.loadForecast(this)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        //TODO fix it
        if (view.id == R.id.item_location_remove) {
            item_location_swipe_layout.close(true)
        }
    }

    fun detachPresenter() {
        presenter?.stop()
    }

    override fun onItemReleased(position: Int) {
        super.onItemReleased(position)
        itemView.isActivated = false
    }

    override fun showCurrentlyWeather(temp: Double, iconCode: String, unitOfTemp: UnitOfTemp) {
        item_location_temp.text = WeatherUtils.formatTemp(itemView.context, unitOfTemp, temp)
        item_location_ico.setImageResource(WeatherUtils.getWeatherIcon(iconCode))
    }

}