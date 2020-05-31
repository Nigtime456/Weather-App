/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations.list

import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.domain.util.UnitFormatHelper
import kotlinx.android.synthetic.main.item_saved_location.view.*

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    LocationItemContract.View {

    private val unitFormatHelper: UnitFormatHelper = UnitFormatHelper(itemView.context)
    var presenter: LocationItemPresenter? = null

    fun bindLocation(location: SavedLocation) {
        itemView.itemSavedLocationName.text = location.getName()
        itemView.itemSavedLocationDescription.text = location.getDescription()
    }

    fun loadForecast() {
        presenter?.loadForecast(this)
    }

    fun detachPresenter() {
        presenter?.clearView()
    }

    override fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp) {
        itemView.itemSavedLocationTemp.text = unitFormatHelper.formatTemp(unitOfTemp, temp)
    }

    override fun showCurrentTempIco(@DrawableRes ico: Int) {
        itemView.itemSavedLocationTempIco.setImageResource(ico)
    }
}