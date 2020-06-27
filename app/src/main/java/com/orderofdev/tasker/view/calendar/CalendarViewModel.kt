package com.orderofdev.tasker.view.calendar

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import com.orderofdev.tasker.BR
import com.orderofdev.tasker.R
import com.orderofdev.tasker.service.DataService
import com.orderofdev.tasker.view.ViewModelAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(private val app: Application) : AndroidViewModel(app) {
    private val data: ObservableArrayList<TaskItemViewModel> = ObservableArrayList()
    val adapter = CalendarAdapter(this, data)
    private val taskService = DataService.getInstance(app)

    init {
        CoroutineScope(Dispatchers.Main).launch {
            loadTestTasks(Date())
        }
    }

    fun loadTestTasks(day : Date) {
         data.clear()
         val list = mutableListOf<TaskItemViewModel>()
         list.add(TaskItemViewModel(0, Date(), Date(), "Почистить зубы", "Необходимо не забыть почистить зубы"))
         list.add(TaskItemViewModel(1, Date(), Date(), "Полить цветы", "Необходимо не забыть полить цветы"))
         data.addAll(list)
    }

    fun loadTasksFromDB(day : Calendar?) {
        CoroutineScope(Dispatchers.Main).launch {
            val smth = taskService.getTasksByDay(day)
        }
    }

}

class CalendarAdapter(
    mainViewModel: CalendarViewModel,
    taskList: ObservableArrayList<TaskItemViewModel>
) : ViewModelAdapter(taskList as ObservableArrayList<Any>) {
    init {
        cell(TaskItemViewModel::class.java, R.layout.task_item, BR.viewModel)
        sharedObject(this, BR.adapter)
        sharedObject(mainViewModel, BR.mainVM)
    }
}
