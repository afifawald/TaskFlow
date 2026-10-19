package com.taskflow.app

import com.taskflow.app.data.model.Category
import com.taskflow.app.data.model.Priority
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PriorityAndCategoryTest {

    @Test
    fun priorities_haveDisplayColorsAndLevels() {
        for (priority in Priority.entries) {
            assertNotNull(priority.getColor())
            assertTrue(priority.displayName.isNotBlank())
            assertTrue(priority.level in 1..4)
        }
    }

    @Test
    fun categories_haveDisplayColors() {
        for (category in Category.entries) {
            assertNotNull(category.getColor())
            assertTrue(category.displayName.isNotBlank())
        }
    }
}
