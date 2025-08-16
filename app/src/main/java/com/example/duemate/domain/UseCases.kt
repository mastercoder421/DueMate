package com.example.duemate.domain

import android.content.Context
import com.example.duemate.data.local.TaskEntity
import com.example.duemate.data.repository.TaskRepository
import com.example.duemate.reminder.ReminderScheduler

class CreateOrUpdateTaskUseCase(
    private val repo: TaskRepository,
    private val appContext: Context
) {
    suspend operator fun invoke(task: TaskEntity): Long {
        val id = if (task.id == 0L) repo.upsert(task) else { repo.update(task); task.id }
        val finalId = if (id == 0L) task.id else id
        ReminderScheduler.cancelAll(appContext, finalId)
        ReminderScheduler.scheduleAll(appContext, finalId, task.title, task.subject, task.deadline)
        return finalId
    }
}

class DeleteTaskUseCase(
    private val repo: TaskRepository,
    private val appContext: Context
) {
    suspend operator fun invoke(taskId: Long) {
        repo.delete(taskId)
        ReminderScheduler.cancelAll(appContext, taskId)
    }
}
