/*
 * Сreated by Igor Pokrovsky. 2020/6/3
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.locations.list

import androidx.annotation.DrawableRes
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.screen.base.BasePresenter

interface LocationViewHolderContract {

    interface View {
        fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp)
        fun showCurrentTempIco(@DrawableRes ico: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun loadForecast(view: View)
    }
}