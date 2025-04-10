package com.crux.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.crux.data.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskEntityDao {

    // 🔹 Write Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE TaskEntity SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTaskCompletion(
        id: Int,
        isCompleted: Boolean
    )

    // 🔹 Read Queries
    @Query("SELECT * FROM TaskEntity ORDER BY createdAt DESC")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("""
        SELECT * FROM TaskEntity 
        ORDER BY 
            CASE 
                WHEN dueDateTime IS NULL THEN 1 
                ELSE 0 
            END,
            dueDateTime ASC
    """)
    fun getAllTasksFlow(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 1 ORDER BY createdAt DESC")
    suspend fun getCompletedTasks(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE isCompleted = 0 ORDER BY createdAt DESC")
    suspend fun getIncompleteTasks(): List<TaskEntity>
}
