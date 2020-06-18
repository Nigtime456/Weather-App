/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/6/15
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.di

import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.mock.FakeForecastSource
import com.github.nigtime456.weather.mock.SniffTrustManager
import com.github.nigtime456.weather.net.repository.ForecastProviderImpl
import com.github.nigtime456.weather.net.repository.ForecastSource
import com.github.nigtime456.weather.net.repository.NetForecastSource
import com.github.nigtime456.weather.net.service.WeatherApi
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

@Module
interface NetModule {

    @ApplicationScope
    @Binds
    fun provideForecastProvider(provider: ForecastProviderImpl): ForecastProvider

    @ApplicationScope
    @Binds
    fun provideNetSource(source: NetForecastSource): ForecastSource

    companion object {
        private const val MAX_REQUEST = 2

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideGson(): Gson {
            return Gson()
        }

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
            return retrofit.create(WeatherApi::class.java)
        }

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.darksky.net")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                //  .sslSocketFactory(getSSLSocketFactory(), SniffTrustManager())
                // .proxy(getSnifferProxy())
                .build()
                .also {
                    it.dispatcher.maxRequests = MAX_REQUEST
                }

        }

        private fun getSSLSocketFactory(): SSLSocketFactory {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(SniffTrustManager()), SecureRandom())
            return sslContext.socketFactory
        }

        private fun getSnifferProxy() =
            Proxy(Proxy.Type.HTTP, InetSocketAddress("192.168.42.8", 8888))
    }
}