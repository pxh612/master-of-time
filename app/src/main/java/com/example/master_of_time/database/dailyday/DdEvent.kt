package com.example.master_of_time.database.dailyday

import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import com.example.master_of_time.database.dailydaygroup.DdGroup


@Entity(
    tableName = DAILY_DAY_TABLE,
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



