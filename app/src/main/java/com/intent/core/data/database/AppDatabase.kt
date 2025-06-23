package com.intent.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intent.core.data.database.dao.TaskEntityDao
import com.intent.core.data.database.dao.TaskListEntityDao
import com.intent.core.data.database.model.TaskEntity
import com.intent.core.data.database.model.TaskListEntity

@Database(
    entities = [
        TaskEntity::class,
        TaskListEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskEntityDao(): TaskEntityDao
    abstract fun taskListEntityDao(): TaskListEntityDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}
