package com.orderofdev.tasker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orderofdev.tasker.service.Converters

@Database(
    entities = [TaskTable::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class TaskerDB : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        private var instance: TaskerDB? = null
        const val TASKER_DATABASE_NAME = "tasker.db"

        fun getInstance(context: Context): TaskerDB {
            if (instance == null) {
                synchronized(TaskerDB::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            TaskerDB::class.java,
                            TASKER_DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return instance!!
        }
    }
}