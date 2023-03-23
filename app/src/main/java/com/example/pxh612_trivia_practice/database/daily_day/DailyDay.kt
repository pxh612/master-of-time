package com.example.pxh612_trivia_practice.database.daily_day

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pxh612_trivia_practice.database.DatabaseNames.DAILY_DAY_TABLE

@Entity(tableName = DAILY_DAY_TABLE)
data class DailyDay (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: Long,
)