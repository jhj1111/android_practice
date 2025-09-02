package com.example.myapplication02.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.sql.Date
import java.time.LocalDateTime

class RoomTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // LocalDateTime 변환기 추가
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun localDateTimeToString(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }
}