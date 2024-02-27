package com.example.yuefengproject.data.factory

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.data.model.EventModel
import com.example.yuefengproject.data.repository.EventRepository

class EventFactory(
    var fragment: Fragment,
    val application: Application,
    val eventRepository: EventRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventModel::class.java)) {
            return EventModel(fragment, application, eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}