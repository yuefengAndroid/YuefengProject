package com.example.yuefengproject.data.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.data.model.MyViewModel
import com.example.yuefengproject.data.repository.MyRepository

class MyFactory (
    private val application: Application,
    private val myRepository: MyRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(application, myRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}