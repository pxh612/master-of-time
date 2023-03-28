package com.example.master_of_time.database.dailyday

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import java.time.Instant
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = DAILY_DAY_TABLE)
data class DailyDay (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "Untitled",
    @ColumnInfo(name = "date")
    var date: Long = 0,
)



