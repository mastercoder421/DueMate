package com.example.duemate.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlin.math.abs

object ReminderScheduler {

    private val OFFSETS = listOf(24,12,6,1).map { it * 60L * 60L * 1000L } // hours -> ms

    fun scheduleAll(context: Context, taskId: Long, title: String, subject: String, deadline: Long) {
        val now = System.currentTimeMillis()
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        fun colorForTrigger(trigger: Long): Int {
            val diff = trigger - now
            return when {
                diff > 12 * 60 * 60 * 1000L -> 0xFF2ECC71.toInt() // green
                diff > 1  * 60 * 60 * 1000L -> 0xFFF1C40F.toInt() // yellow
                else -> 0xFFE74C3C.toInt() // red
            }
        }

        OFFSETS.forEachIndexed { idx, offset ->
            val triggerAt = deadline - offset
            if (triggerAt > now) {
                am.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    pending(context, taskId, title, subject, colorForTrigger(triggerAt), req(taskId, idx))
                )
            }
        }

        val overdueAt = deadline + 60_000 // overdue ping
        if (overdueAt > now) {
            val idx = OFFSETS.size
            am.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                overdueAt,
                pending(context, taskId, title, subject, 0xFFE74C3C.toInt(), req(taskId, idx))
            )
        }
    }

    fun cancelAll(context: Context, taskId: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        (0..OFFSETS.size).forEach { idx ->
            am.cancel(pending(context, taskId, "", "", 0, req(taskId, idx)))
        }
    }

    private fun pending(
        context: Context,
        taskId: Long,
        title: String,
        subject: String,
        color: Int,
        reqCode: Int
    ): PendingIntent {
        val i = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("taskId", taskId)
            putExtra("title", title)
            putExtra("subject", subject)
            putExtra("color", color)
            putExtra("notifId", reqCode)
        }
        return PendingIntent.getBroadcast(
            context,
            reqCode,
            i,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun req(taskId: Long, index: Int): Int {
        val base = (taskId % 1_000_000L).toInt()
        return abs(base * 10 + index)
    }
}
