package com.orderofdev.tasker.view.calendar

import androidx.databinding.ObservableField
import java.util.Date

class TaskItemViewModel(
    val id: Long,
    startTime: Date,
    finishTime: Date,
    name: String,
    description: String
) {
    val startTime: ObservableField<Date> = ObservableField(startTime)
    val finishTime: ObservableField<Date> = ObservableField(finishTime)
    val name = ObservableField(name)
    val description = ObservableField(description)
}