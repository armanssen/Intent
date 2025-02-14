package com.crux.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.crux.data.database.model.TaskEntity

@Dao
interface TaskEntityDao {

    // 🔹 Write Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("UPDATE TaskEntity SET isCompleted = 1 WHERE id = :id")
    suspend fun markTaskAsCompleted(id: Int)

    @Query("UPDATE TaskEntity SET isCompleted = 0 WHERE id = :id")
    suspend fun markTaskAsIncomplete(id: Int)

    // 🔹 Read Queries
    @Query("SELECT * FROM TaskEntity ORDER BY createdAt DESC")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 1 ORDER BY createdAt DESC")
    suspend fun getCompletedTasks(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 0 ORDER BY createdAt DESC")
    suspend fun getIncompleteTasks(): List<TaskEntity>
}
