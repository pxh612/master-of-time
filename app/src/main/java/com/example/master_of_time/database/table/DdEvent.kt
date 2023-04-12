package com.example.master_of_time.database.table

import androidx.room.*
import java.time.Instant


@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DdGroup::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId")
        )
    )
)
data class DdEvent (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var date: Long = Instant.now().epochSecond,

    @ColumnInfo
    var groupId: Long? = null,

    @ColumnInfo
    var calculationTypeId: Int = 0
)