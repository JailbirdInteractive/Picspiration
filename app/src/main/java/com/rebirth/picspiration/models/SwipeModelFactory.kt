package com.rebirth.picspiration.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SwipeModelFactory(private val application: Application, private val myExtraParam: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SwipeViewModel(application, myExtraParam) as T
}

