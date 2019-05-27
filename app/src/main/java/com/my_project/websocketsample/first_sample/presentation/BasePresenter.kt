package com.my_project.websocketsample.first_sample.presentation

/**
 * Created Максим on 26.05.2019.
 * Copyright © Max
 */
open class BasePresenter<T> {

    protected var view: T? = null

    fun bindView(view: T) {
        this.view = view
    }

    fun unbindView() {
        this.view = null
    }

}