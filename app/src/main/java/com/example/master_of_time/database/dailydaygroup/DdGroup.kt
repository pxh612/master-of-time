package com.example.master_of_time.database.dailydaygroup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Room use className as tableName by default
@Entity
data class DdGroup(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var order: Int = id,
)
