package com.example.todoapp.add_task.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskentity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

}