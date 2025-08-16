package com.example.duemate.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.duemate.R

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra("taskId", -1L)
        val title  = intent.getStringExtra("title") ?: "Task"
        val subject= intent.getStringExtra("subject") ?: "DueMate"
        val color  = intent.getIntExtra("color", Color.GRAY)
        val notifId= intent.getIntExtra("notifId", (taskId % Int.MAX_VALUE).toInt())

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannel(nm)

        // TODO: Replace with an activity that opens task detail later
        val contentIntent = PendingIntent.getActivity(
            context, 0, Intent(), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("$subject • $title")
            .setContentText("Deadline reminder")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(color)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
            .build()

        nm.notify(notifId, notif)
    }

    private fun createChannel(nm: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(
                CHANNEL_ID, "DueMate Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Deadline reminders and overdue alerts"
            }
            nm.createNotificationChannel(ch)
        }
    }

    companion object { const val CHANNEL_ID = "duemate_reminders" }
}
