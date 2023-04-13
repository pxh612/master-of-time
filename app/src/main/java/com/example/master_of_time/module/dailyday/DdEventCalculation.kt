package com.example.master_of_time.module.dailyday

import com.example.master_of_time.plural
import com.example.master_of_time.toDateFormat
import com.example.master_of_time.toZonedDateTime
import java.lang.IllegalArgumentException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DdEventCalculation(
    private val index: Int,
    private val date: Long
) {

    data class DdEventCalculationType(
        val name: String = "",
        val description: String = ""
    )


    companion object {
        val GivenList: List<DdEventCalculationType> = listOf(
            DdEventCalculationType( "ONE TIME", "You only live once"),
            DdEventCalculationType( "MONTHLY", "Remind each month"),
            DdEventCalculationType( "ANNUALLY", "Remind each year"),
        )

        fun findIdByName(name: String): Int {
            val result = GivenList.indexOfFirst { name == it.name }
            if(result == -1) throw Exception("No found DdEventCalculationType of \"$name\"")
            else return result
        }

    }



    val ddEventCalculationType: DdEventCalculationType
        get() = GivenList[index]
    
    val zonedDateTime: ZonedDateTime
        get() = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault())
    
    val nowZonedDateTime: ZonedDateTime
        get() = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())

    val targetDate: Long 
        get() = when(ddEventCalculationType.name){
            "ONE TIME" -> {
                date
            }
            "MONTHLY" -> {
                var targetZonedDateTime = zonedDateTime
                while (targetZonedDateTime.isAfter(nowZonedDateTime)) targetZonedDateTime = targetZonedDateTime.minusMonths(1)
                while(targetZonedDateTime.isBefore(nowZonedDateTime)) targetZonedDateTime = targetZonedDateTime.plusMonths(1)
                targetZonedDateTime.toEpochSecond()
            }
            "ANNUALLY" -> {
                var targetZonedDateTime = zonedDateTime
                while (targetZonedDateTime.isAfter(nowZonedDateTime)) targetZonedDateTime = targetZonedDateTime.minusYears(1)
                while(targetZonedDateTime.isBefore(nowZonedDateTime)) targetZonedDateTime = targetZonedDateTime.plusYears(1)
                targetZonedDateTime.toEpochSecond()
            }
            else -> throw IllegalArgumentException()
        }

    fun ZonedDateTime.toEpochDay(): Long{
        this.run{
            return LocalDate.of(year, monthValue, dayOfMonth).toEpochDay()
        }
    }
    
    fun getDayDistanceFromPresent(date: Long = targetDate): Long {
        return date.toZonedDateTime().toEpochDay() - nowZonedDateTime.toEpochDay()
    }

    private fun displayDayDistanceFromEpochDays(date: Long): CharSequence {
        if(date < 0){
            var dateFinal = -date
            val dateString = dateFinal.toString()
            return "$dateString day${plural(dateFinal)} ago"
        }
        else {
            var dateFinal = date
            val dateString = dateFinal.toString()
            if (dateFinal == 0L) return "Today"
            else return "$dateString day${plural(dateFinal)} left"
        }
    }
    
    private fun displayDayDistanceFromPresent(date: Long): CharSequence {
        return displayDayDistanceFromEpochDays(getDayDistanceFromPresent(date))
    }

    fun displayPickedDate(): String{
        return when(ddEventCalculationType.name){
            "ONE TIME" -> date.toDateFormat()
            "MONTHLY" -> {
                zonedDateTime.run {
                    "$dayOfMonth every month"
                }
            }
            "ANNUALLY" -> {
                zonedDateTime.run {
                    "$dayOfMonth/$monthValue every year"
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    fun displaySearchedDate(): CharSequence {
        return targetDate.toDateFormat()
    }

    fun displayCalculation(): CharSequence? {
        return displayDayDistanceFromPresent(targetDate)
    }

}
/*

Fix:
databinding all


Utilities:
sort
layout
drag groups
        */
