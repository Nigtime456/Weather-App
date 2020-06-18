/*
 * Сreated by Igor Pokrovsky. 2020/6/3
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.locations.list

import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.screen.base.BasePresenter

interface LocationViewHolderContract {

    interface View {
        fun showCurrentlyWeather(temp: Double, iconCode: String, unitOfTemp: UnitOfTemp)
    }

    interface Presenter : BasePresenter {
        fun loadForecast(view: View)
    }
}