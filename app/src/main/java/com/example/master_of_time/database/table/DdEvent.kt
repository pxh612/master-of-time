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
    var id: Int = 0,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var date: Long = Instant.now().epochSecond,

    @ColumnInfo
    var groupId: Int = -1,
)



/*

[ksp] /home/pxh612/AndroidStudioProjects/master-of-time/app/src/main/java/com/example/master_of_time/database/ddevent/DdEvent.kt:19:
 groupId column references a foreign key but it is not part of an index.
 This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.

 */
