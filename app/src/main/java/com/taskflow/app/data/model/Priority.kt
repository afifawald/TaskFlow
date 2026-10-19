package com.taskflow.app.data.model

import androidx.compose.ui.graphics.Color

enum class Priority(val displayName: String, val level: Int) {
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3),
    URGENT("Urgent", 4);

    fun getColor(): Color {
        return when (this) {
            LOW -> Color(0xFF4CAF50)      // Green
            MEDIUM -> Color(0xFF2196F3)   // Blue
            HIGH -> Color(0xFFFF9800)     // Amber/Orange
            URGENT -> Color(0xFFE91E63)   // Red/Pink
        }
    }
}
