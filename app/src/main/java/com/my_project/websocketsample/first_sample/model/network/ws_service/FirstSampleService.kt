package com.my_project.websocketsample.first_sample.model.network.ws_service

import com.my_project.websocketsample.API
import com.my_project.websocketsample.first_sample.model.network.ws.RxWebSocket
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class FirstSampleService {

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private fun provideRequest(): Request = Request.Builder().url(API.BASE_URL).build()

    fun provideWS(): RxWebSocket = RxWebSocket(provideOkHttpClient(), provideRequest())

}