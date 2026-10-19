package com.taskflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.taskflow.app.ui.screens.TaskListScreen
import com.taskflow.app.ui.screens.TaskViewModel
import com.taskflow.app.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TaskViewModel by viewModels {
        val app = application as TaskFlowApplication
        TaskViewModel.Factory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TaskFlowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    TaskListScreen(
                        uiState = uiState,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}
