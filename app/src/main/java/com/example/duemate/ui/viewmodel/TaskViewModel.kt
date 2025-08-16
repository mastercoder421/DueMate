package com.example.duemate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.duemate.data.local.TaskEntity
import com.example.duemate.data.repository.TaskRepository
import com.example.duemate.domain.CreateOrUpdateTaskUseCase
import com.example.duemate.domain.DeleteTaskUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repo: TaskRepository,
    private val createOrUpdate: CreateOrUpdateTaskUseCase,
    private val deleteTask: DeleteTaskUseCase
) : ViewModel() {

    val tasks = repo.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addOrUpdate(task: TaskEntity) = viewModelScope.launch {
        createOrUpdate(task)
    }

    fun delete(id: Long) = viewModelScope.launch {
        deleteTask(id)
    }
}

class TaskVMFactory(
    private val repo: TaskRepository,
    private val createOrUpdate: CreateOrUpdateTaskUseCase,
    private val deleteTask: DeleteTaskUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TaskViewModel(repo, createOrUpdate, deleteTask) as T
}
