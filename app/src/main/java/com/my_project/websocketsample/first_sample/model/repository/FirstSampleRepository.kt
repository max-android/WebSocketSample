package com.my_project.websocketsample.first_sample.model.repository

import com.my_project.websocketsample.first_sample.model.network.ws_service.FirstSampleService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class FirstSampleRepository {

    private val networkService = FirstSampleService()
    val ws = networkService.provideWS()

    fun connectWS() = ws.connect()

    fun closeWS() = ws
        .close()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun request(request: Any) = ws
        .sendMessage(request)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun response() = ws
        .onTextMessage()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}