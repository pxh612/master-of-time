package com.example.master_of_time.database.dailyday

import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import com.example.master_of_time.database.dailydaygroup.DdGroup
import java.time.Instant


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
    var title: String?,

    @ColumnInfo
    var date: Long = Instant.now().epochSecond,

    @ColumnInfo
    var groupId: Int? = null,
)



