package com.orderofdev.tasker.view.calendar

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.orderofdev.tasker.R
import com.orderofdev.tasker.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment() {
    lateinit var viewModel: CalendarViewModel
    lateinit var binding: FragmentCalendarBinding

    companion object {
        const val TAG = "CalendarFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application =
            this.activity!!.application ?: inflater.context.applicationContext as Application
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        val provider = ViewModelProvider(this, factory)
        viewModel = provider.get(CalendarViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.mainVM = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskCalendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                taskList.visibility = View.VISIBLE
                viewModel.loadTestTasks(eventDay.calendar.time)
            }
        })
    }
}