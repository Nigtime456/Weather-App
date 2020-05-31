/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations.list

import androidx.annotation.DrawableRes
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BasePresenter

interface LocationItemContract {

    interface View {
        fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp)
        fun showCurrentTempIco(@DrawableRes ico: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun loadForecast(view: View)
        fun clearView()
    }
}