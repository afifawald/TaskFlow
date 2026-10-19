package com.taskflow.app.data.repository

import com.taskflow.app.data.local.TaskDao
import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override fun searchTasks(category: Category, query: String): Flow<List<TaskEntity>> {
        val categoryFilter = if (category == Category.ALL) "ALL" else category.name
        return taskDao.searchTasks(categoryFilter, query.trim())
    }

    override suspend fun getTaskById(taskId: Long): TaskEntity? = taskDao.getTaskById(taskId)

    override suspend fun insertTask(task: TaskEntity): Long = taskDao.insertTask(task)

    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

    override suspend fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) {
        taskDao.setTaskCompleted(taskId, isCompleted)
    }

    override suspend fun clearCompletedTasks() {
        taskDao.clearCompletedTasks()
    }
}
