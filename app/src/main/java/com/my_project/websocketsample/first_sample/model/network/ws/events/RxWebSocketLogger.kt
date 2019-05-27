package com.my_project.websocketsample.first_sample.model.network.ws.events

import android.util.Log
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class RxWebSocketLogger(val tag: String) : Subscriber<SocketEvent> {

    init {
        Log.d("--LOG", "RxWebSocketLogger-$tag")
    }

    override fun onComplete() {
        Log.d("--LOG", "RxWebSocketLogger-onComplete()")
    }

    override fun onSubscribe(s: Subscription?) {
        Log.d("--LOG", "RxWebSocketLogger-onSubscribe")
    }

    override fun onNext(t: SocketEvent?) {
        Log.d("--LOG", "RxWebSocketLogger-onNext: " + t.toString())
    }

    override fun onError(t: Throwable?) {
        Log.d("--LOG", "RxWebSocketLogger-onError: " + t?.message)
    }
}