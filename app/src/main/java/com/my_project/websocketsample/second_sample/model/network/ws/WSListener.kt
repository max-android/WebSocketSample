package com.my_project.websocketsample.second_sample.model.network.ws

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.io.IOException

/**
 * Created Максим on 27.05.2019.
 * Copyright © Max
 */
class WSListener: WebSocketListener() {

    private val connectionRelay = BehaviorRelay.create<Boolean>()
    private val dataRelay = PublishRelay.create<Any>()
    private val dataHandler = dataRelay.toFlowable(BackpressureStrategy.BUFFER)
        .map{ if(it is String) return@map it as String else throw IOException("WS Error", it as Throwable) }

    init {
        connectionRelay.accept(false)
    }

    fun observeConnectionStatus(): Observable<Boolean> = connectionRelay

    fun observeData(): Flowable<String> = dataHandler

    fun isConnected():Boolean = connectionRelay.value

    override fun onOpen(webSocket: WebSocket, response: Response) {
       Log.i("--LOG","WS---onOpen")
        connectionRelay.accept(true)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.i("--LOG","WS---onFailure")
        dataRelay.accept(t)
        connectionRelay.accept(false)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.i("--LOG","WS---onClosing")
        connectionRelay.accept(false)
        webSocket.close(code, reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.i("--LOG","WS---onMessage---text$text")
        dataRelay.accept(text)
    }

}