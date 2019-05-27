package com.my_project.websocketsample.second_sample.model.repository

import android.annotation.SuppressLint
import com.my_project.websocketsample.second_sample.model.entities.BaseRequest
import com.my_project.websocketsample.second_sample.model.entities.BaseResponse
import com.my_project.websocketsample.second_sample.model.network.ws_service.SecondSampleService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created Максим on 27.05.2019.
 * Copyright © Max
 */
class SecondSampleRepository {

   private val ws = SecondSampleService().provideWS()

     @SuppressLint("CheckResult")
     fun request(data:BaseRequest):Single<BaseResponse>{
        return ws.observeConnectionStatus()
             .doOnSubscribe{ ws.connect() }
             .filter{connected -> connected}
             .firstOrError()
             .flatMap { ws.sendWSRequest(data) }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
     }

    fun closeWS(){
          ws.disconnect()
    }
}