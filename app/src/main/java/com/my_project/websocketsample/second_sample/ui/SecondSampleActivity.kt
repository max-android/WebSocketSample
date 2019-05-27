package com.my_project.websocketsample.second_sample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.my_project.websocketsample.R
import com.my_project.websocketsample.first_sample.model.entities.Person
import com.my_project.websocketsample.second_sample.model.entities.BaseRequest
import com.my_project.websocketsample.second_sample.presentation.SecondSamplePresenter
import com.my_project.websocketsample.second_sample.presentation.SecondSampleView

class SecondSampleActivity : AppCompatActivity(),SecondSampleView {

    private val secondSamplePresenter = SecondSamplePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_sample)
        secondSamplePresenter.bindView(this)
        secondSamplePresenter.request(BaseRequest("type2",Person("имя",28)))
    }


    override fun onDestroy() {
        super.onDestroy()
        secondSamplePresenter.unbindView()
        secondSamplePresenter.clear()
    }

    override fun showMessage(message: Any) {
        Log.d("--LOG", "---Activity---Success---onTextMessage()---$message")
    }

    override fun showError(error: String) {
        Log.d("--LOG", "---Activity---Error---onTextMessage()---$error")
    }
}
