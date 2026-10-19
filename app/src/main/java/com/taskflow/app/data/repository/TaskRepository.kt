package com.taskflow.app.data.repository

import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    fun searchTasks(category: Category, query: String): Flow<List<TaskEntity>>
    suspend fun getTaskById(taskId: Long): TaskEntity?
    suspend fun insertTask(task: TaskEntity): Long
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean)
    suspend fun clearCompletedTasks()
}
