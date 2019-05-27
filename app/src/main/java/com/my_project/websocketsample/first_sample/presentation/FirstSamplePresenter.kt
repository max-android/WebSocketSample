package com.my_project.websocketsample.first_sample.presentation

import android.util.Log
import com.my_project.websocketsample.first_sample.model.repository.FirstSampleRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
class FirstSamplePresenter: BasePresenter<FirstSampleView>() {

    private val mainRepository = FirstSampleRepository()
    private val compositeDisposable = CompositeDisposable()

    fun connect() = mainRepository.connectWS()

    fun request(request:Any) = compositeDisposable.add(mainRepository
        .request(request)
        .subscribe(
            { messageResponse() },
            { view?.showError(it.message.toString()) }
        ))

    fun messageResponse() = compositeDisposable.add(mainRepository
        .response()
        .subscribe(
            { view?.showMessage(it.text) },
            { error -> Log.d("--LOG","onTextMessage()-${error.message}") }
        ))

    fun close() = compositeDisposable.add(mainRepository
        .closeWS()
        .subscribe(
        {  },
        { error -> Log.d("--LOG","onTextMessage()-${error.message}") }
    ))

    fun clear(){
        compositeDisposable.clear()
    }

}