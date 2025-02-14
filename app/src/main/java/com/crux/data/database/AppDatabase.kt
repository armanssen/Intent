package com.crux.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crux.data.database.dao.TaskEntityDao
import com.crux.data.database.model.TaskEntity

@Database(
    entities = [
        TaskEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskEntityDao(): TaskEntityDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}
