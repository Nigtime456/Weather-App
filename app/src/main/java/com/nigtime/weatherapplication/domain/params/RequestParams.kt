
/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.params

import com.nigtime.weatherapplication.domain.settings.Lang

sealed class RequestParams constructor(val lang: Lang) {
    class CityParams(lang: Lang,val cityId: Long) : RequestParams(lang)
}