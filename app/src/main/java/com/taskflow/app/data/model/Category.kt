package com.taskflow.app.data.model

import androidx.compose.ui.graphics.Color

enum class Category(val displayName: String) {
    ALL("All"),
    WORK("Work"),
    PERSONAL("Personal"),
    FITNESS("Fitness"),
    STUDY("Study"),
    FINANCE("Finance"),
    OTHER("Other");

    fun getColor(): Color {
        return when (this) {
            ALL -> Color(0xFF6750A4)
            WORK -> Color(0xFF00796B)      // Teal
            PERSONAL -> Color(0xFF6200EE)  // Purple
            FITNESS -> Color(0xFFE65100)   // Deep Orange
            STUDY -> Color(0xFF1565C0)     // Indigo
            FINANCE -> Color(0xFF2E7D32)   // Dark Green
            OTHER -> Color(0xFF607D8B)     // Blue Grey
        }
    }
}
