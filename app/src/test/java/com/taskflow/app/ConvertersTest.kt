package com.taskflow.app

import com.taskflow.app.data.local.Converters
import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.Priority
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun priorityConversion_roundTrip() {
        for (priority in Priority.entries) {
            val stringVal = converters.fromPriority(priority)
            val recovered = converters.toPriority(stringVal)
            assertEquals(priority, recovered)
        }
    }

    @Test
    fun categoryConversion_roundTrip() {
        for (category in Category.entries) {
            val stringVal = converters.fromCategory(category)
            val recovered = converters.toCategory(stringVal)
            assertEquals(category, recovered)
        }
    }

    @Test
    fun invalidPriority_fallbackToMedium() {
        val result = converters.toPriority("INVALID_VALUE")
        assertEquals(Priority.MEDIUM, result)
    }

    @Test
    fun invalidCategory_fallbackToOther() {
        val result = converters.toCategory("UNKNOWN_CATEGORY")
        assertEquals(Category.OTHER, result)
    }
}
