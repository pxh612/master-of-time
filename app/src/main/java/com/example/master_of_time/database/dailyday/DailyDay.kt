package com.example.master_of_time.database.dailyday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE

@Entity(tableName = DAILY_DAY_TABLE)
data class DailyDay (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: Long,
)