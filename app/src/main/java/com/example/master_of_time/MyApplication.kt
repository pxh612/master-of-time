package com.example.master_of_time

import android.text.Editable
import android.widget.DatePicker
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
/*
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
    }
}
*/

fun Long.toOffsetDateTime(): OffsetDateTime{
    return Instant.ofEpochSecond(this)
        .atOffset(ZoneOffset.UTC)
}
fun Long.toDateFormat(): String {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault()).run{
        "$dayOfMonth/$monthValue/$year"
    }
}
fun Long.toZonedDateTime(): ZonedDateTime{
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
}

fun OffsetDateTime.toDateFormat(): String{
    return format( DateTimeFormatter.ISO_LOCAL_DATE )
        .replace( "T" , " " )
}

fun DatePicker.toEpochTimeSeconds(): Long {
    return LocalDate.of(year, month+1, dayOfMonth).atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun formatDate(year: Int, month: Int, dayOfMonth: Int): String{
    return "$dayOfMonth/$month/$year"
}