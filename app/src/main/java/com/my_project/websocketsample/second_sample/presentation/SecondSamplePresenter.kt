package com.my_project.websocketsample.second_sample.presentation

import com.my_project.websocketsample.first_sample.presentation.BasePresenter
import com.my_project.websocketsample.second_sample.model.entities.BaseRequest
import com.my_project.websocketsample.second_sample.model.repository.SecondSampleRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created Максим on 27.05.2019.
 * Copyright © Max
 */
class SecondSamplePresenter: BasePresenter<SecondSampleView>() {

    private val compositeDisposable = CompositeDisposable()
    private val secondRepository = SecondSampleRepository()

    fun request(data:BaseRequest){
        compositeDisposable.add(
            secondRepository.request(data)
                .subscribe(
                    { view?.showMessage(it) },
                    { view?.showError(it.message.toString()) }
                ))
    }

    fun clear(){
        secondRepository.closeWS()
        compositeDisposable.clear()
    }

}