package com.crux.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.crux.core.data.database.model.TaskListEntity
import com.crux.core.data.database.model.TaskListWithCountQueryResult
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListEntityDao {

    // 🔹 Write Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskList: TaskListEntity): Long

    @Update
    suspend fun update(taskList: TaskListEntity)

    @Query("UPDATE TaskListEntity SET name = :name WHERE id = :id")
    suspend fun updateName(
        id: Int,
        name: String
    )

    @Query("DELETE FROM TaskListEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    // 🔹 Read Queries
    @Query("SELECT * FROM TaskListEntity")
    fun getAllFlow(): Flow<List<TaskListEntity>>

    @Query("""
        SELECT 
            TaskListEntity.*,
            COUNT(CASE WHEN TaskEntity.isCompleted = 0 THEN 0 END) AS taskCount 
        FROM TaskListEntity
        LEFT JOIN TaskEntity 
            ON TaskListEntity.id = TaskEntity.listId
        GROUP BY TaskListEntity.id
    """)
    fun getAllFlowWithTaskCount(): Flow<List<TaskListWithCountQueryResult>>
}
