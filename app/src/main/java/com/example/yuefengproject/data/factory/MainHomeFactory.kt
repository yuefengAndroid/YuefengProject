package com.example.yuefengproject.data.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.data.model.MainHomeViewModel

class MainHomeFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainHomeViewModel::class.java)) {
            return MainHomeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}