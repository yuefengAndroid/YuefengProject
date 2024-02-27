package com.example.yuefengproject.ui.events

import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.BaseFragment
import com.example.yuefengproject.R
import com.example.yuefengproject.data.factory.EventFactory
import com.example.yuefengproject.data.model.EventModel
import com.example.yuefengproject.data.repository.EventRepository
import com.example.yuefengproject.databinding.FragmentEventsBinding

class EventsFragment : BaseFragment<FragmentEventsBinding>() {

    override val resId: Int = R.layout.fragment_events

    override fun initAfterBinding() {
        val eventRepository = EventRepository()
        val eventFactory = EventFactory(this, activity.application, eventRepository)
        val eventModel = ViewModelProvider(this, eventFactory)[EventModel::class.java]
        binding.eventModel = eventModel
    }
}