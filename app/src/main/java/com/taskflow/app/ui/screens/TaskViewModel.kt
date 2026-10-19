package com.taskflow.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.Priority
import com.taskflow.app.data.model.TaskEntity
import com.taskflow.app.data.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(Category.ALL)
    private val _searchQuery = MutableStateFlow("")
    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Closed)

    private sealed interface DialogState {
        data object Closed : DialogState
        data object AddNew : DialogState
        data class Edit(val task: TaskEntity) : DialogState
    }

    private val _tasksFlow = combine(_selectedCategory, _searchQuery) { category, query ->
        category to query
    }.flatMapLatest { (category, query) ->
        repository.searchTasks(category, query)
    }

    val uiState: StateFlow<TaskUiState> = combine(
        _tasksFlow,
        _selectedCategory,
        _searchQuery,
        _dialogState
    ) { tasks, category, query, dialogState ->
        val pending = tasks.filter { !it.isCompleted }
        val completed = tasks.filter { it.isCompleted }

        TaskUiState(
            pendingTasks = pending,
            completedTasks = completed,
            selectedCategory = category,
            searchQuery = query,
            editingTask = (dialogState as? DialogState.Edit)?.task,
            isAddDialogOpen = dialogState is DialogState.AddNew || dialogState is DialogState.Edit,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskUiState(isLoading = true)
    )

    fun onEvent(event: TaskUiEvent) {
        when (event) {
            is TaskUiEvent.SearchQueryChanged -> {
                _searchQuery.value = event.query
            }
            is TaskUiEvent.CategorySelected -> {
                _selectedCategory.value = event.category
            }
            is TaskUiEvent.ToggleCompletion -> {
                viewModelScope.launch {
                    repository.toggleTaskCompletion(event.task.id, !event.task.isCompleted)
                }
            }
            is TaskUiEvent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(event.task)
                }
            }
            is TaskUiEvent.SaveTask -> {
                viewModelScope.launch {
                    if (event.taskId != null && event.taskId > 0) {
                        val currentTask = repository.getTaskById(event.taskId)
                        if (currentTask != null) {
                            val updated = currentTask.copy(
                                title = event.title,
                                description = event.description,
                                priority = event.priority,
                                category = event.category
                            )
                            repository.updateTask(updated)
                        }
                    } else {
                        val newTask = TaskEntity(
                            title = event.title,
                            description = event.description,
                            priority = event.priority,
                            category = event.category
                        )
                        repository.insertTask(newTask)
                    }
                    _dialogState.value = DialogState.Closed
                }
            }
            is TaskUiEvent.OpenAddDialog -> {
                _dialogState.value = DialogState.AddNew
            }
            is TaskUiEvent.OpenEditDialog -> {
                _dialogState.value = DialogState.Edit(event.task)
            }
            is TaskUiEvent.DismissDialog -> {
                _dialogState.value = DialogState.Closed
            }
            is TaskUiEvent.ClearCompletedTasks -> {
                viewModelScope.launch {
                    repository.clearCompletedTasks()
                }
            }
        }
    }

    class Factory(private val repository: TaskRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
