package com.taskflow.app.ui.screens

import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.Priority
import com.taskflow.app.data.model.TaskEntity

data class TaskUiState(
    val pendingTasks: List<TaskEntity> = emptyList(),
    val completedTasks: List<TaskEntity> = emptyList(),
    val selectedCategory: Category = Category.ALL,
    val searchQuery: String = "",
    val editingTask: TaskEntity? = null,
    val isAddDialogOpen: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface TaskUiEvent {
    data class SearchQueryChanged(val query: String) : TaskUiEvent
    data class CategorySelected(val category: Category) : TaskUiEvent
    data class ToggleCompletion(val task: TaskEntity) : TaskUiEvent
    data class DeleteTask(val task: TaskEntity) : TaskUiEvent
    data class SaveTask(
        val taskId: Long? = null,
        val title: String,
        val description: String,
        val priority: Priority,
        val category: Category
    ) : TaskUiEvent
    data class OpenEditDialog(val task: TaskEntity) : TaskUiEvent
    data object OpenAddDialog : TaskUiEvent
    data object DismissDialog : TaskUiEvent
    data object ClearCompletedTasks : TaskUiEvent
}
