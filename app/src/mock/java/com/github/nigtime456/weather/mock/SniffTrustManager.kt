/*
 * Сreated by Igor Pokrovsky. 2020/6/15
 */

package com.github.nigtime456.weather.mock

import android.annotation.SuppressLint
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

@SuppressLint("TrustAllX509TrustManager")
class SniffTrustManager : X509TrustManager {

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        //trust any certificate
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        //trust any certificate
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return emptyArray()
    }
}