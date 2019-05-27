package com.my_project.websocketsample.first_sample.model.network.ws

import android.util.Log
import com.google.gson.Gson
import com.my_project.websocketsample.first_sample.model.network.ws.events.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import org.reactivestreams.Publisher

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class RxWebSocket(private val client: OkHttpClient, private val request: Request) {

    private val webSocketOnSubscribe: WebSocketOnSubscribe = WebSocketOnSubscribe(client, request)
    private var socketEventProcessor = PublishProcessor.create<SocketEvent>()
    private val compositeDisposable = CompositeDisposable()
    private var webSocket: WebSocket? = null

    private fun getEventSource(): Flowable<SocketEvent> {
        return socketEventProcessor.onErrorResumeNext(Function<Throwable, Publisher<SocketEvent>> {
            Log.d("--LOG", "RxWebSocket-getEventSource()-onErrorResumeNext-${it.message}")
            socketEventProcessor = PublishProcessor.create<SocketEvent>()
            socketEventProcessor
        })
    }

    fun onOpen(): Flowable<SocketOpenEvent> {
        return getEventSource()
            .ofType(SocketOpenEvent::class.java)
            .doOnEach(RxWebSocketLogger("onOpen"))
    }

    fun onClosed(): Flowable<SocketClosedEvent> {
        return getEventSource()
            .ofType(SocketClosedEvent::class.java)
            .doOnEach(RxWebSocketLogger("onClosed"))
    }

    fun onClosing(): Flowable<SocketClosingEvent> {
        return getEventSource()
            .ofType(SocketClosingEvent::class.java)
            .doOnEach(RxWebSocketLogger("onClosing"))
    }

    fun onFailure(): Flowable<SocketFailureEvent> {
        return getEventSource()
            .ofType(SocketFailureEvent::class.java)
            .doOnEach(RxWebSocketLogger("onFailure"))
    }

    fun onTextMessage(): Flowable<SocketMessageEvent> {
        return getEventSource()
            .ofType(SocketMessageEvent::class.java)
            .doOnEach(RxWebSocketLogger("onTextMessage"))
    }

    @Synchronized
    fun connect() {
        val connectionDisposable: Disposable = Flowable.create(webSocketOnSubscribe, BackpressureStrategy.BUFFER)
            .doOnNext { Log.d("--LOG", "Intro-CONNECT---$it") }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(
                { event ->
                    run {
                        socketEventProcessor.onNext(event)
                        Log.d("--LOG", "RxWebSocket-connect()-$event")
                    }
                },
                { error -> Log.d("--LOG", "RxWebSocket-connect()-${error.message}") }
            )

        compositeDisposable.add(connectionDisposable)

        val webSocketInstanceDisposable: Disposable = getEventSource()
            .ofType(SocketOpenEvent::class.java)
            .doOnNext { Log.d("--LOG", "Intro-Open---$it") }
            .firstElement()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(
                { socketOpenEvent ->
                    webSocket = socketOpenEvent.webSocket
                    Log.d("--LOG", "RxWebSocket-Instance-connect()-${webSocket.toString()}")
                },
                { error -> Log.d("--LOG", "RxWebSocket-Instance-connect()-${error.message}") }
            )

        compositeDisposable.add(webSocketInstanceDisposable)
    }

    @Synchronized
    fun sendMessage(any: Any): Single<Boolean> {
        return Single.fromCallable {
            webSocket?.let {
                val jsonBody: String = Gson().toJson(any)
                it.send(jsonBody)
            }
        }
    }

    @Synchronized
    fun sendMessage(content: String): Single<Boolean> {
        Log.d("--LOG", "RxWebSocket-sendMessage--------$content----" + webSocket.toString())
        return Single.fromCallable {
            webSocket?.send(content)
        }
    }

    @Synchronized
    fun sendMessage(bytes: ByteString): Single<Boolean> {
        return Single.fromCallable {
            webSocket?.send(bytes)
        }
    }

    @Synchronized
    fun close(): Single<Boolean?> {
        return Single.fromCallable {
            webSocket?.let {
                compositeDisposable.add(getEventSource()
                    .ofType(SocketClosedEvent::class.java)
                    .subscribe(
                        { compositeDisposable.clear() },
                        { error -> Log.d("--LOG", "RxWebSocket-close()-${error.message}") }
                    ))
                it.close(1000, "Bye")
            }
        }.doOnSuccess { webSocket = null }
    }

    @Synchronized
    fun close(code: Int, reason: String): Single<Boolean?> {
        return Single.fromCallable {
            webSocket?.let {
                compositeDisposable.add(getEventSource()
                    .ofType(SocketClosedEvent::class.java)
                    .subscribe(
                        { compositeDisposable.clear() },
                        { error -> Log.d("--LOG", "RxWebSocket-close()-${error.message}") }
                    ))
                it.close(code, reason)
            }
        }.doOnSuccess { webSocket = null }
    }
}