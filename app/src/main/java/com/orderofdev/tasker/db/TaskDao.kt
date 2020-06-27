package com.orderofdev.tasker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskTable WHERE strftime('%s', :day) BETWEEN strftime('%s', startDate) AND strftime('%s', finishDate)")
    fun findTasksByDay (day : Calendar?): List<TaskTable>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun save(task: TaskTable): Long
}