package com.example.yuefengproject.data.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.data.model.HomeViewModel
import com.example.yuefengproject.data.repository.HomeRepository

class HomeFactory(
    private val application: Application,
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application, homeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}