package com.taskflow.app

import android.app.Application
import com.taskflow.app.data.local.TaskDatabase
import com.taskflow.app.data.repository.TaskRepository
import com.taskflow.app.data.repository.TaskRepositoryImpl

class TaskFlowApplication : Application() {

    val database: TaskDatabase by lazy {
        TaskDatabase.getDatabase(this)
    }

    val repository: TaskRepository by lazy {
        TaskRepositoryImpl(database.taskDao())
    }
}
