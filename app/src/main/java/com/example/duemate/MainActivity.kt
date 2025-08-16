package com.example.duemate

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.duemate.data.local.TaskEntity
import com.example.duemate.ui.theme.DueMateTheme
import com.example.duemate.ui.viewmodel.TaskVMFactory
import com.example.duemate.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {

    // ✅ Hook into your DI / App graph
    private val vm: TaskViewModel by viewModels {
        val graph = (application as DueMateApp).graph
        TaskVMFactory(graph.taskRepo, graph.createOrUpdateTask, graph.deleteTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        askNotificationPermission()
        suggestExactAlarmIfNeeded()

        setContent {
            DueMateTheme {
                val tasks by vm.tasks.collectAsState(initial = emptyList())

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { insertSample() }) {
                            Text("+")
                        }
                    }
                ) { inner ->
                    TaskList(
                        tasks = tasks,
                        onDelete = { vm.delete(it) },
                        modifier = Modifier
                            .padding(inner)
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
            }
        }
    }

    /** ✅ Add sample task (for testing reminders) */
    private fun insertSample() {
        val now = System.currentTimeMillis()
        val t = TaskEntity(
            subject = "Math",
            title = "Algebra worksheet",
            deadline = now + 60 * 60 * 1000L // 1 hour from now
        )
        vm.addOrUpdate(t)
    }

    /** ✅ Request Notification Permission (Android 13+) */
    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* You can show a toast if denied */ }

    /** ✅ Ask user for exact alarm permission (Android 12+) */
    private fun suggestExactAlarmIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val am = getSystemService(AlarmManager::class.java)
            if (!am.canScheduleExactAlarms()) {
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        }
    }
}

@Composable
private fun TaskList(
    tasks: List<TaskEntity>,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text("Your Tasks", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks) { t ->
                ElevatedCard {
                    Column(Modifier.padding(12.dp)) {
                        Text("${t.subject} • ${t.title}", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Deadline: ${
                                java.text.DateFormat.getDateTimeInstance()
                                    .format(java.util.Date(t.deadline))
                            }"
                        )
                        Spacer(Modifier.height(8.dp))
                        Row {
                            TextButton(onClick = { onDelete(t.id) }) { Text("Delete") }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("Tap + to add a sample task (schedules reminders at 24/12/6/1h + overdue).")
    }
}
