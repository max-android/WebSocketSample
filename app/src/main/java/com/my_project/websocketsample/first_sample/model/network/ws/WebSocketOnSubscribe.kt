package com.my_project.websocketsample.first_sample.model.network.ws

import com.my_project.websocketsample.first_sample.model.network.ws.events.SocketEvent
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class WebSocketOnSubscribe(private val client: OkHttpClient, private val request: Request): FlowableOnSubscribe<SocketEvent> {

    override fun subscribe(emitter: FlowableEmitter<SocketEvent>) {
        client.newWebSocket(request, WebSocketEventListener(emitter))
    }
}