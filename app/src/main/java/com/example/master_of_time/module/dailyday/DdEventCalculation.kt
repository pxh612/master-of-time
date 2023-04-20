package com.example.master_of_time.module.dailyday

import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.plural
import com.example.master_of_time.toDateFormat
import com.example.master_of_time.toZonedDateTime
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

// primary constructor?: unique & non-redudant
class DdEventCalculation(
    private val calculationTypeId: Int,
    private val date: Long
) {
    constructor(ddEvent: DdEvent) : this(ddEvent.calculationTypeId, ddEvent.date)
    constructor(date: Long) : this(calculationTypeId = -1, date = date)

    data class DdEventCalculationType(
        val id: Int,
        val name: String = "",
        val description: String = "",
    )

    companion object {
        val ONE_TIME = DdEventCalculationType( 0, "ONE TIME", "You only live once")
        val MONTHLY = DdEventCalculationType( 1, "MONTHLY", "Remind each month")
        val YEARLY = DdEventCalculationType( 2, "ANNUALLY", "Remind each year")

        fun findIdByName(name: String): Int {
            val result = GivenList.indexOfFirst { name == it.name }
            if(result == -1) throw Exception("No found DdEventCalculationType of \"$name\"")
            else return result
        }
        private val GivenList: List<DdEventCalculationType> = listOf(ONE_TIME, MONTHLY, YEARLY)
    }

    val MissingCalculationTypeException = Exception("missing calculationTypeId")
    private val JUMP_LENGTH = 100

    var zonedDateTime: ZonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault())
    val nowZonedDateTime: ZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
    val targetDate: Long by lazy {
        when (calculationTypeId) {
            ONE_TIME.id -> {
                date
            }
            MONTHLY.id -> {
                // TODO: nowZonedDateTime is always after targetZonedDateTime, so it will be off by one calculation even when they're in the same day
                var targetZonedDateTime = zonedDateTime

                while (targetZonedDateTime.isAfter(nowZonedDateTime)) targetZonedDateTime =
                    targetZonedDateTime.minusMonths(1)

                while (targetZonedDateTime.isBefore(nowZonedDateTime)) targetZonedDateTime =
                    targetZonedDateTime.plusMonths(1)

                targetZonedDateTime.toEpochSecond()
            }
            YEARLY.id -> {
                var targetZonedDateTime = zonedDateTime

                while (targetZonedDateTime.isAfter(nowZonedDateTime)) targetZonedDateTime =
                    targetZonedDateTime.minusYears(1)

                while (targetZonedDateTime.isBefore(nowZonedDateTime)) targetZonedDateTime =
                    targetZonedDateTime.plusYears(1)

                targetZonedDateTime.toEpochSecond()
            }
            else -> throw MissingCalculationTypeException
        }
    }



    fun ZonedDateTime.toEpochDay(): Long{
        this.run{
            return LocalDate.of(year, monthValue, dayOfMonth).toEpochDay()
        }
    }

    fun minus(value: Long): Long{
        return when(calculationTypeId){
            ONE_TIME.id -> zonedDateTime.minusDays(value*JUMP_LENGTH).toEpochSecond()
            MONTHLY.id -> zonedDateTime.minusMonths(value).toEpochSecond()
            YEARLY.id -> zonedDateTime.minusYears(value).toEpochSecond()
            else -> throw MissingCalculationTypeException
        }
    }
    fun plus(value: Long): Long{
        return when(calculationTypeId){
            ONE_TIME.id -> zonedDateTime.plusDays(value*JUMP_LENGTH).toEpochSecond()
            MONTHLY.id -> zonedDateTime.plusMonths(value).toEpochSecond()
            YEARLY.id -> zonedDateTime.plusYears(value).toEpochSecond()
            else -> throw MissingCalculationTypeException
        }
    }

    private fun getDayDistanceFromPresent(): Long {
        return date.toZonedDateTime().toEpochDay() - nowZonedDateTime.toEpochDay()
    }
    fun getDayDistanceFromPresentAbsolute(): Long {
        var distance = getDayDistanceFromPresent()
        if(distance < 0) distance = -distance
        return distance
    }
    fun displayPickedDate(): String{
        return when(calculationTypeId){
            ONE_TIME.id -> date.toDateFormat()
            MONTHLY.id -> zonedDateTime.run {
                "$dayOfMonth every month"
            }
            YEARLY.id -> zonedDateTime.run {
                "$dayOfMonth/$monthValue every year"
            }
            else -> throw MissingCalculationTypeException
        }
    }

    private fun displayDayDistanceFromEpochDays(date: Long): CharSequence {
        if(date < 0){
            val dateFinal = -date
            val dateString = dateFinal.toString()
            return "$dateString day${plural(dateFinal)} ago"
        }
        else {
            val dateFinal = date
            val dateString = dateFinal.toString()
            if (dateFinal == 0L) return "Today"
            else return "$dateString day${plural(dateFinal)} left"
        }
    }
    fun displayDayDistanceFromPresent(): CharSequence {
        return displayDayDistanceFromEpochDays(getDayDistanceFromPresent())
    }

}