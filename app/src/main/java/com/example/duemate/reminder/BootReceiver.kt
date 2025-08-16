package com.example.duemate.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.duemate.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val dao = AppDatabase.get(context).taskDao()

        CoroutineScope(Dispatchers.IO).launch {
            val now = System.currentTimeMillis()
            val tasks = dao.dueAfter(now)
            tasks.forEach { t ->
                ReminderScheduler.scheduleAll(
                    context = context,
                    taskId = t.id,
                    title = t.title,
                    subject = t.subject,
                    deadline = t.deadline
                )
            }
        }
    }
}
