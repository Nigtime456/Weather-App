/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory private constructor(private val retrofit: Retrofit) {

    fun getApi(): WeatherApi = retrofit.create(WeatherApi::class.java)

    companion object {
        fun getInstance(): ApiFactory {
            return ApiFactory(getRetrofit())
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.weatherbit.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient())
                .build()
        }

        private fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
    }
}

