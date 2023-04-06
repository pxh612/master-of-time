package com.example.master_of_time.module.dailyday

import android.icu.util.IslamicCalendar.CalculationType

class DdEventCalculation {
    val DAY_UNTIL = 0
    val DAY_PAST = 1
    val MONTHLY = 2
    val ANNUALLY = 3


    var calculationType: Int = 0
    var displayText: String? = null
        get() = when(calculationType){
            DAY_UNTIL -> {
                "day until"
            }
            DAY_PAST -> {
                "day past"
            }
            MONTHLY -> "monthly"
            ANNUALLY -> "annually"
            else -> throw IllegalArgumentException()
        }


}