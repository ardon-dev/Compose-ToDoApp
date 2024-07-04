package com.example.todoapp.add_task.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

}