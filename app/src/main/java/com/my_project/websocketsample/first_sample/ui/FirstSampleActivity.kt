package com.my_project.websocketsample.first_sample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.my_project.websocketsample.R
import com.my_project.websocketsample.first_sample.model.entities.Person
import com.my_project.websocketsample.first_sample.presentation.FirstSamplePresenter
import com.my_project.websocketsample.first_sample.presentation.FirstSampleView
import kotlinx.android.synthetic.main.activity_main.*

class FirstSampleActivity : AppCompatActivity(), FirstSampleView {

    private lateinit var mainPresenter: FirstSamplePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        mainPresenter = FirstSamplePresenter()
        mainPresenter.bindView(this)
        mainPresenter.connect()
        buttonStart.setOnClickListener { mainPresenter.request(Person("имя-1", 23)) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.close()
        mainPresenter.clear()
        mainPresenter.unbindView()
    }

    override fun showMessage(message: String) {
        Log.d("--LOG", "---Activity---Success---onTextMessage()---$message")
    }

    override fun showError(error: String) {
        Log.d("--LOG", "---Activity---Error---onTextMessage()---$error")
    }

}
