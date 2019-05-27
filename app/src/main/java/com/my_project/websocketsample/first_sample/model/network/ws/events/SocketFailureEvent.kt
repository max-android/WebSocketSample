package com.my_project.websocketsample.first_sample.model.network.ws.events


import com.my_project.websocketsample.first_sample.model.network.ws.SocketEventTypeEnum
import okhttp3.Response

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class SocketFailureEvent(val throwable: Throwable, val response: Response) : SocketEvent(SocketEventTypeEnum.FAILURE) {

    override fun toString(): String {
        return "SocketFailureEvent(throwable=${throwable.message})"
    }
}