package com.orderofdev.tasker.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TaskTable (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val startDate: Calendar?,
    val finishDate: Calendar?,
    val startTime: Calendar?,
    val finishTime: Calendar?,
    val name: String,
    val description: String
)