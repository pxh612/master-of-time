package com.example.master_of_time

import android.app.Application
import android.text.Editable
import android.widget.DatePicker
import java.time.*
import java.time.LocalTime.ofInstant
import java.time.format.DateTimeFormatter

class MyApplication: Application(){
    companion object{
        val WORKING_MODE = false
    }
}

fun Long.toOffsetDateTime(): OffsetDateTime{
    return Instant.ofEpochSecond(this)
        .atOffset(ZoneOffset.UTC)
}
fun Long.toDateFormat(): String {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault()).run{
        "$dayOfMonth/$monthValue/$year"
    }
}
fun Long.toEpochDay(): Long{
    val zdt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
    return zdt.toLocalDate().toEpochDay()
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

fun plural(count: Long): String{
    return when{
        (-1L <= count && count <= 1L) -> ""
        else -> "s"
    }
}
fun plural(count: Int): String{
    return when{
        (-1 <= count && count <= 1) -> ""
        else -> "s"
    }
}