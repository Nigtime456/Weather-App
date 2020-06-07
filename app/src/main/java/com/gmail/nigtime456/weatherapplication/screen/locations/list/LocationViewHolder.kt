/*
 * Сreated by Igor Pokrovsky. 2020/6/2
 */


package com.gmail.nigtime456.weatherapplication.screen.locations.list

import android.view.View
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.data.util.UnitFormatter
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
        setDragHandleView(itemLocationDragHandle)
    }

    fun attachPresenter() {
        presenter?.loadForecast(this)
    }

    fun detachPresenter() {
        presenter?.stop()
    }

    override fun onItemReleased(position: Int) {
        super.onItemReleased(position)
        itemView.isActivated = false
    }

    override fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp) {
        itemLocationTemp.text =
            UnitFormatter.formatTemp(itemView.context, unitOfTemp, temp)
    }

    override fun showCurrentTempIco(ico: Int) {
        itemLocationTempIco.setImageResource(ico)
    }
}