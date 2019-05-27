package com.my_project.websocketsample.first_sample.model.network.ws.events

import com.my_project.websocketsample.first_sample.model.network.ws.SocketEventTypeEnum


/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
open class SocketEvent(private val type: SocketEventTypeEnum) {
    override fun toString(): String {
        return "SocketEvent(type=$type)"
    }
}