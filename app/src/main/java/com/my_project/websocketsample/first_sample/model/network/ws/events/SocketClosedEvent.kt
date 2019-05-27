package com.my_project.websocketsample.first_sample.model.network.ws.events

import com.my_project.websocketsample.first_sample.model.network.ws.SocketEventTypeEnum

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class SocketClosedEvent(val code:Int,val reason:String):SocketEvent(SocketEventTypeEnum.CLOSED) {
    override fun toString(): String {
        return "SocketClosingEvent(code=$code, reason='$reason')"
    }
}