package com.my_project.websocketsample.first_sample.model.network.ws.events


import com.my_project.websocketsample.first_sample.model.network.ws.SocketEventTypeEnum
import okio.ByteString

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class SocketMessageEvent : SocketEvent {

    lateinit var text: String
     lateinit var bytes: ByteString

    constructor(text: String) : super(SocketEventTypeEnum.MESSAGE) {
        this.text = text
    }

    constructor(bytes: ByteString) : super(SocketEventTypeEnum.MESSAGE) {
        this.bytes = bytes
    }

}