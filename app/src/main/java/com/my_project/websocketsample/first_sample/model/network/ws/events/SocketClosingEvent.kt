package com.my_project.websocketsample.first_sample.model.network.ws.events

import com.my_project.websocketsample.first_sample.model.network.ws.SocketEventTypeEnum

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class SocketClosingEvent(val code:Int,val reason:String) :SocketEvent(SocketEventTypeEnum.CLOSING) {
    override fun toString(): String {
        return "SocketClosingEvent(code=$code, reason='$reason')"
    }
}