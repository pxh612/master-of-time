package com.example.master_of_time.module.dailyday

import com.example.master_of_time.toDateFormat
import com.example.master_of_time.toZonedDateTime
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DdEventCalculation(
    private val index: Int,
    private val date: Long
) {

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



    val EPOCHDAYS = 60*60*24
    val ddEventCalculationType: DdEventCalculationType
        get() = GivenList[index]
    val zonedDateTime: ZonedDateTime
        get() = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault())
    val nowZonedDateTime: ZonedDateTime
        get() = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())

    fun plural(date: Long): String{
        return when{
            (date <= 1L) -> ""
            else -> "s"
        }
    }


    fun getSearchedDate(): Long{
        return when(ddEventCalculationType.name){
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
    }

    private fun displayDayDistance(date: Long): CharSequence {
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

    private fun displayDayDistanceFromPresent(date: Long): CharSequence? {
        return displayDayDistance(date.toZonedDateTime().toEpochDay() - nowZonedDateTime.toEpochDay())
    }

    fun ZonedDateTime.toEpochDay(): Long{
        this.run{
            return LocalDate.of(year, monthValue, dayOfMonth).toEpochDay()
        }
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
        return getSearchedDate().toDateFormat()
    }

    fun displayCalculation(): CharSequence? {
        return displayDayDistanceFromPresent(getSearchedDate())
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
