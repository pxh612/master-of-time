package com.example.master_of_time

import android.app.Application
import android.text.Editable
import android.widget.DatePicker
import com.jakewharton.threetenabp.AndroidThreeTen
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
    return Instant.ofEpochSecond(this)
        .atOffset(ZoneOffset.UTC)
}
fun Long.toDateFormat(): String {
    return toOffsetDateTime().toDateFormat()
}

fun OffsetDateTime.toDateFormat(): String{
    return format( DateTimeFormatter.ISO_LOCAL_DATE )
        .replace( "T" , " " )
}

fun DatePicker.toEpochTimeSeconds(): Long {
    /**
    Bug: calendar (no ZoneOffset) convert to Java.Time.Instant (with ZoneOffSet) make dayResult off by one day
    Fixed (temporary): add 7 hours to calendar when convert
     */


    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth, 7, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis / 1000
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
internal fun DatePicker.toDateFormat(): String = toEpochTimeSeconds().toOffsetDateTime().toDateFormat()
