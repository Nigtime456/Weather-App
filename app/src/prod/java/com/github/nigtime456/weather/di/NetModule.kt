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
                .build()
                .also {
                    it.dispatcher.maxRequests = MAX_REQUEST
                }

        }

    }
}