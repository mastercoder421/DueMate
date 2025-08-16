package com.example.duemate

import android.app.Application
import android.content.Context
import com.example.duemate.data.local.AppDatabase
import com.example.duemate.data.repository.TaskRepository
import com.example.duemate.domain.CreateOrUpdateTaskUseCase
import com.example.duemate.domain.DeleteTaskUseCase

class DueMateApp : Application() {
    lateinit var graph: AppGraph
        private set

    override fun onCreate() {
        super.onCreate()
        graph = AppGraph(this)
    }
}

class AppGraph(private val context: Context) {
    private val db by lazy { AppDatabase.get(context) }
    val taskRepo by lazy { TaskRepository(db.taskDao()) }

    // Use cases you already have
    val createOrUpdateTask by lazy { CreateOrUpdateTaskUseCase(taskRepo, context) }
    val deleteTask by lazy { DeleteTaskUseCase(taskRepo, context) }
}
