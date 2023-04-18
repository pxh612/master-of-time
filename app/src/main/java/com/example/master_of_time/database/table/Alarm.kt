package com.example.master_of_time.database.table

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Alarm (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo
    var description: String = "",

    @ColumnInfo
    var alarmTime: Long = 0,
    // get epochSecond in a day

    @ColumnInfo
    var alarmMethod: Int,

    @ColumnInfo
    var ringtonePath: String? = null,

    @ColumnInfo
    var daysInWeek: Int,

    @ColumnInfo
    var repetitionType: Int,

    @ColumnInfo
    var isEnabled: Boolean,
)