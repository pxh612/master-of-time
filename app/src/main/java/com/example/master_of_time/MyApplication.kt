package com.example.master_of_time

import android.app.Application
import android.os.Build
import android.text.Editable
import android.util.Log
import android.widget.DatePicker
import com.example.master_of_time.database.dailyday.DailyDay
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /** ThreeTen Android Backport: backport for java.time
         * https://github.com/JakeWharton/ThreeTenABP
         */

        AndroidThreeTen.init(this);
    }
}

fun Long.toOffsetDateTime(): OffsetDateTime{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this)
            .atOffset(ZoneOffset.UTC)
        /**
        Bug: calendar (no ZoneOffset) convert to Java.Time.Instant (with ZoneOffSet) make dayResult off by one day
        Fixed (temporary): add 7 hours to calendar when convert
         */

    } else throw Exception("API Level is lower than 26")
}


fun OffsetDateTime.toDateFormat(): String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        format( DateTimeFormatter.ISO_LOCAL_DATE )
            .replace( "T" , " " )

    } else throw Exception("API Level is lower than 26")
}

fun DatePicker.toEpochTimeSeconds(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth, 7, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis / 1000
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)