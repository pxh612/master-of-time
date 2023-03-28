package com.example.master_of_time.database.dailyday

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.room.*
import com.example.master_of_time.database.DatabaseNames.DAILY_DAY_TABLE
import java.time.Instant
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = DAILY_DAY_TABLE)
data class DailyDay (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "Untitled",
    @ColumnInfo(name = "date")
    var date: Long = 0,
)



fun DailyDay.getDateString(): String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(date)
            .atOffset(ZoneOffset.UTC)
            .format( DateTimeFormatter.ISO_LOCAL_DATE )
            .replace( "T" , " " )

        /**
         Bug calendar (no ZoneOffset) convert to Java.Time.Instant (with ZoneOffSet) make dayResult off by one day
         Fixed (temporary): add 7 hours to calendar when convert
         */

    } else throw Exception("API Level is lower than 26")
}




