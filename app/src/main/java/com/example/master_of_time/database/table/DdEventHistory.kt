package com.example.master_of_time.database.table

import androidx.room.*
import java.time.Instant


@Entity(
    foreignKeys = [ForeignKey(
        entity = DdEvent::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("eventId")
    )]
)
data class DdEventHistory (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo
    var eventId: Long,

    @ColumnInfo
    var date: Long = Instant.now().epochSecond,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var description: String = "",
)