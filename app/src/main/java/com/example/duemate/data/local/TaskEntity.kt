package com.example.duemate.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.duemate.data.model.TaskStatus

@Entity(
    tableName = "tasks",
    indices = [Index("deadline"), Index("status"), Index("subject")]
)

data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id:Long=0L,
    val subject: String,
    val title:String,
    val description: String?=null,
    val deadline: Long,
    val createdAt: Long=System.currentTimeMillis(),
    val updateAt: Long=System.currentTimeMillis(),
    val status: TaskStatus = TaskStatus.NOT_STARTED,
    val imageUris: List<String> = emptyList(),
    val updatedAt: Long = System.currentTimeMillis()
)