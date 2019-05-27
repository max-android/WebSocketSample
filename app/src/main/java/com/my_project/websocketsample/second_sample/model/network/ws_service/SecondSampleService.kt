package com.my_project.websocketsample.second_sample.model.network.ws_service

import com.google.gson.Gson
import com.my_project.websocketsample.API
import com.my_project.websocketsample.second_sample.model.network.ws.WSConnectionManager
import com.my_project.websocketsample.second_sample.model.network.ws.WSListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created Максим on 27.05.2019.
 * Copyright © Max
 */
class SecondSampleService {

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private fun provideRequest(): Request = Request.Builder().url(API.BASE_URL).build()

    fun provideWS(): WSConnectionManager = WSConnectionManager(provideOkHttpClient(), WSListener(), Gson(),provideRequest())

}