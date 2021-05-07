package com.example.todoappmvp.data.local

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var dataBase: ToDoDatabase
//    private val context: Context = mock()
//    private val task = Task("title", "description", "id", true)

    @Before
    fun setUp() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Application>(),
            ToDoDatabase::class.java
        ).build()
    }

    @After
    internal fun tearDown() {
        dataBase.close()
    }

    @Test
    fun `getTasks`() {

//        val result = dataBase.taskDao().getTasks()

//        val result = dataBase.taskDao().getTasks()

//        assertEquals(task, result)
    }
}
