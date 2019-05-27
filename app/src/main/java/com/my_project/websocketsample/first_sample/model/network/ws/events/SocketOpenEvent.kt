package com.my_project.websocketsample.first_sample.model.network.ws.events

import com.my_project.websocketsample.first_sample.model.network.ws.SocketEventTypeEnum
import okhttp3.Response
import okhttp3.WebSocket

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class SocketOpenEvent(val webSocket: WebSocket,val response: Response): SocketEvent(SocketEventTypeEnum.OPEN) {
    override fun toString(): String {
        return "SocketOpenEvent(webSocket=$webSocket, response=$response)"
    }
}