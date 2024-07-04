package com.example.todoapp.add_task.data.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.add_task.data.TaskDao
import com.example.todoapp.add_task.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ToDoDatabase {
        return Room.databaseBuilder(
            appContext,
            ToDoDatabase::class.java,
            "TaskDataBase"
        ).build()
    }

    @Provides
    fun provideTaskDao(toDoDatabase: ToDoDatabase): TaskDao {
        return toDoDatabase.taskDao()
    }

}