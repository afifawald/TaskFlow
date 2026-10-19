package com.taskflow.app

import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.Priority
import com.taskflow.app.data.model.TaskEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskEntityTest {

    @Test
    fun defaultValues_areCorrect() {
        val task = TaskEntity(title = "Buy groceries")

        assertEquals("Buy groceries", task.title)
        assertEquals("", task.description)
        assertEquals(Priority.MEDIUM, task.priority)
        assertEquals(Category.PERSONAL, task.category)
        assertFalse(task.isCompleted)
        assertTrue(task.createdAt > 0)
    }

    @Test
    fun copyTask_updatesCompletionCorrectly() {
        val original = TaskEntity(title = "Read a book", isCompleted = false)
        val completed = original.copy(isCompleted = true)

        assertFalse(original.isCompleted)
        assertTrue(completed.isCompleted)
        assertEquals(original.title, completed.title)
    }
}
