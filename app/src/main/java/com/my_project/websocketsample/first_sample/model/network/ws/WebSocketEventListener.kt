package com.my_project.websocketsample.first_sample.model.network.ws

import com.my_project.websocketsample.first_sample.model.network.ws.events.*
import io.reactivex.FlowableEmitter
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class WebSocketEventListener(private val emitter: FlowableEmitter<SocketEvent>):WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        if (!emitter.isCancelled) {
            emitter.onNext(SocketOpenEvent(webSocket, response))
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        if (!emitter.isCancelled && response!=null) {
            emitter.onNext(SocketFailureEvent(t, response))
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        if (!emitter.isCancelled) {
            emitter.onNext(SocketClosingEvent(code, reason))
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        if (!emitter.isCancelled) {
            emitter.onNext(SocketMessageEvent(text))
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        if (!emitter.isCancelled) {
            emitter.onNext(SocketMessageEvent(bytes))
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        if (!emitter.isCancelled) {
            emitter.onNext(SocketClosedEvent(code, reason))
        }
    }
}