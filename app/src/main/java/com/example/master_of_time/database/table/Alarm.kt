package com.example.master_of_time.database.table

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.Instant

data class Alarm (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var alarmTime: Long = 0,
    // get epochSecond in a day

    @ColumnInfo
    var ringtone: Long,

    @ColumnInfo
    var daysInWeek: Int,

    @ColumnInfo
    var isOneTime: Boolean,

    @ColumnInfo
    var isEnabled: Boolean

)