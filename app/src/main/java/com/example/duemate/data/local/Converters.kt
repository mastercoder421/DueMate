package com.example.duemate.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.duemate.data.model.TaskStatus
import kotlin.collections.emptyList

class Converters {
    private val gson = Gson()

    @TypeConverter fun fromStatus(s: TaskStatus) = s.name
    @TypeConverter fun toStatus(v: String) = TaskStatus.valueOf(v)

    @TypeConverter fun fromList(list : List<String>?)=gson.toJson(list ?: emptyList<String>())
    @TypeConverter fun toList(json: String): List<String>{
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(json,type)?:emptyList()
    }
}