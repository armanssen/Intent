package com.crux.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.crux.data.database.model.TaskListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListEntityDao {

    // 🔹 Write Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskList: TaskListEntity)

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
}
