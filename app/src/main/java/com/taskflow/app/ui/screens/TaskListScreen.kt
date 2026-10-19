package com.taskflow.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taskflow.app.R
import com.taskflow.app.data.model.Category
import com.taskflow.app.ui.components.CategoryChip
import com.taskflow.app.ui.components.TaskDialog
import com.taskflow.app.ui.components.TaskItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    uiState: TaskUiState,
    onEvent: (TaskUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSearchExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearchExpanded) {
                        OutlinedTextField(
                            value = uiState.searchQuery,
                            onValueChange = { onEvent(TaskUiEvent.SearchQueryChanged(it)) },
                            placeholder = { Text(stringResource(R.string.search_hint)) },
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (uiState.searchQuery.isNotEmpty()) {
                                        onEvent(TaskUiEvent.SearchQueryChanged(""))
                                    } else {
                                        isSearchExpanded = false
                                    }
                                }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Close search")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp)
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TaskAlt,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                actions = {
                    if (!isSearchExpanded) {
                        IconButton(onClick = { isSearchExpanded = true }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search tasks")
                        }
                    }
                    if (uiState.completedTasks.isNotEmpty()) {
                        IconButton(onClick = { onEvent(TaskUiEvent.ClearCompletedTasks) }) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "Clear completed tasks",
                                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TaskUiEvent.OpenAddDialog) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_task))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Category Filter Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Category.entries.forEach { category ->
                    CategoryChip(
                        category = category,
                        isSelected = category == uiState.selectedCategory,
                        onClick = { onEvent(TaskUiEvent.CategorySelected(category)) }
                    )
                }
            }

            val totalTasks = uiState.pendingTasks.size + uiState.completedTasks.size

            if (totalTasks == 0) {
                // Empty State
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.no_tasks_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.no_tasks_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (uiState.pendingTasks.isNotEmpty()) {
                        item {
                            Text(
                                text = "${stringResource(R.string.pending_tasks)} (${uiState.pendingTasks.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        items(
                            items = uiState.pendingTasks,
                            key = { it.id }
                        ) { task ->
                            TaskItemCard(
                                task = task,
                                onToggleCompletion = { onEvent(TaskUiEvent.ToggleCompletion(task)) },
                                onClick = { onEvent(TaskUiEvent.OpenEditDialog(task)) },
                                onDelete = { onEvent(TaskUiEvent.DeleteTask(task)) }
                            )
                        }
                    }

                    if (uiState.completedTasks.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${stringResource(R.string.completed_tasks)} (${uiState.completedTasks.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        items(
                            items = uiState.completedTasks,
                            key = { it.id }
                        ) { task ->
                            TaskItemCard(
                                task = task,
                                onToggleCompletion = { onEvent(TaskUiEvent.ToggleCompletion(task)) },
                                onClick = { onEvent(TaskUiEvent.OpenEditDialog(task)) },
                                onDelete = { onEvent(TaskUiEvent.DeleteTask(task)) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Add / Edit Task Dialog
    AnimatedVisibility(
        visible = uiState.isAddDialogOpen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        TaskDialog(
            initialTask = uiState.editingTask,
            onDismiss = { onEvent(TaskUiEvent.DismissDialog) },
            onSave = { title, description, priority, category ->
                onEvent(
                    TaskUiEvent.SaveTask(
                        taskId = uiState.editingTask?.id,
                        title = title,
                        description = description,
                        priority = priority,
                        category = category
                    )
                )
            }
        )
    }
}
