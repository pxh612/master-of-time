package com.example.master_of_time.database.dailyday

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import com.example.master_of_time.database.dailydaygroup.DailyDayGroup
import java.time.Instant
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


@Entity(
    tableName = DAILY_DAY_TABLE,
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DailyDayGroup::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId")
        )
    )
)
data class DailyDay (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var title: String = "Untitled",

    @ColumnInfo
    var date: Long = 0,

    @ColumnInfo
    var groupId: Int? = null,

//    @ForeignKey.Action.

//    @Target(allowedTargets = DAILY_DAY_TABLE) // ?
//    @Retention(value = AnnotationRetention.BINARY)
//    annotation ForeignKey.id: Int
)



