/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherbitapp.uselles.storage.utils

import android.content.Context
import java.io.InputStream


//TODO USELESS
class AssetCityProvider constructor(val context: Context) {

    fun getZippedCitiesDictionary(): InputStream {
        return context.assets.open("cities_full.zip")
    }
}