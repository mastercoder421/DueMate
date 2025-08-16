package com.example.duemate.data.local.dao

import androidx.room.*
import com.example.duemate.data.local.TaskEntity
import com.example.duemate.data.model.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: TaskEntity): Long

    @Update suspend fun update(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM tasks ORDER BY deadline ASC")
    fun observeAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): TaskEntity?

    @Query("UPDATE tasks SET status = :status, updatedAt = :now WHERE id = :id")
    suspend fun updateStatus(id: Long, status: TaskStatus, now: Long = System.currentTimeMillis())

    @Query("""
        SELECT * FROM tasks
        WHERE (subject LIKE '%' || :q || '%'
           OR title LIKE '%' || :q || '%'
           OR description LIKE '%' || :q || '%')
        ORDER BY deadline ASC
    """)
    fun search(q: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE status = :status ORDER BY deadline ASC")
    fun filterByStatus(status: TaskStatus): Flow<List<TaskEntity>>

    @Query("SELECT COUNT(*) FROM tasks WHERE deadline < :now AND status != 'COMPLETED'")
    suspend fun countOverdue(now: Long): Int

    // Used after device reboot to re-schedule only future tasks
    @Query("SELECT * FROM tasks WHERE deadline > :now ORDER BY deadline ASC")
    suspend fun dueAfter(now: Long): List<TaskEntity>
}