package com.my_project.websocketsample.second_sample.model.network.ws

import android.util.Log
import com.google.gson.Gson
import com.my_project.websocketsample.second_sample.model.entities.BaseRequest
import com.my_project.websocketsample.second_sample.model.entities.BaseResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

/**
 * Created Максим on 27.05.2019.
 * Copyright © Max
 */
class WSConnectionManager(
    private val client: OkHttpClient,
    private val wsListener: WSListener,
    private val gson: Gson,
    private val okhttp3Request: Request
) {

    private var webSocket: WebSocket? = null
    fun isConnected(): Boolean = webSocket != null && wsListener.isConnected()

    fun connect(): Boolean {
        if (!isConnected()) {
            webSocket = client.newWebSocket(okhttp3Request, wsListener)
            return true
        }
        return false
    }


    fun disconnect() {
        if (isConnected()) {
            client.dispatcher().executorService().shutdown()
        }
    }

    fun observeConnectionStatus(): Observable<Boolean> = wsListener.observeConnectionStatus().distinctUntilChanged()

    @Synchronized
    fun send(any: Any): Single<Boolean> {
        return Single.fromCallable {
            webSocket?.let {
                val jsonBody: String = gson.toJson(any)
                Log.i("--LOG", " webSocket!!.send(data)")
                it.send(jsonBody)
            }
        }
    }

    fun sendWSRequest(request: BaseRequest): Single<BaseResponse> {
        return send(request)
            .toFlowable()
            .flatMap { wsListener.observeData() }
            .map { json -> BaseResponse(gson.fromJson(json, BaseRequest::class.java).request) }
            .firstOrError()
    }

}