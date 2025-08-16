package com.example.duemate.data.repository

import com.example.duemate.data.local.dao.TaskDao
import com.example.duemate.data.local.TaskEntity
import com.example.duemate.data.model.TaskStatus
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    fun observeAll(): Flow<List<TaskEntity>> = dao.observeAll()
    fun search(q: String): Flow<List<TaskEntity>> = dao.search(q)
    fun filterByStatus(status: TaskStatus): Flow<List<TaskEntity>> = dao.filterByStatus(status)

    suspend fun upsert(task: TaskEntity): Long =
        dao.upsert(task.copy(updatedAt = System.currentTimeMillis()))

    suspend fun update(task: TaskEntity) =
        dao.update(task.copy(updatedAt = System.currentTimeMillis()))

    suspend fun updateStatus(id: Long, status: TaskStatus) = dao.updateStatus(id, status)
    suspend fun delete(id: Long) = dao.deleteById(id)
    suspend fun overdueCount(): Int = dao.countOverdue(System.currentTimeMillis())
}