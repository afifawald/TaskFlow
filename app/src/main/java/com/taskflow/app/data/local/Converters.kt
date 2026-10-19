package com.taskflow.app.data.local

import androidx.room.TypeConverter
import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.Priority

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(value: String): Priority {
        return try {
            Priority.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Priority.MEDIUM
        }
    }

    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        return try {
            Category.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Category.OTHER
        }
    }
}
