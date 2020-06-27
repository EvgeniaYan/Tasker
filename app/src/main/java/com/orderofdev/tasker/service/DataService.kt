package com.orderofdev.tasker.service

import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.WorkerThread
import com.orderofdev.tasker.db.TaskerDB
import java.util.*

class DataService(private val context: Context) {
    private val db = TaskerDB.getInstance(context)
    private val taskDao = db.getTaskDao()

    private val cw = ContextWrapper(context.applicationContext)

    companion object {
        var instance: DataService? = null

        fun getInstance(context: Context): DataService {
            if (instance == null) {
                synchronized(DataService::class) {
                    instance = instance ?: DataService(context)
                }
            }
            return instance!!
        }
    }

    @WorkerThread
    fun getTasksByDay(day: Calendar?) = taskDao.findTasksByDay(day)

}