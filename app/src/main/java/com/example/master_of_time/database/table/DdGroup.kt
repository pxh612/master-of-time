package com.example.master_of_time.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Room use className as tableName by default
@Entity
data class DdGroup(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var orderId: Long = 0,
)
