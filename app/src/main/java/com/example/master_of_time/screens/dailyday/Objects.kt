package com.example.master_of_time.screens.dailyday




object TABLE {
    fun cycleThrough(state: Int, totalState: Int = 2): Int {
        return (state+1)%totalState
    }

    var TABLE_DAILY_DAY = 0
    var TABLE_DAILY_DAY_GROUP = 1
}

