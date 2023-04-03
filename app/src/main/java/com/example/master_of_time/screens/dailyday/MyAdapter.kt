package com.example.master_of_time.screens.dailyday

import android.content.Context
import android.widget.Adapter


class MyAdapter(
    private val context: Context
) {

    companion object{
        private val ITEM_DAILYDAY = 0
        private val ITEM_DAILYDAY_GROUP = 1
        private val ITEMS = 2
    }

    var value: Adapter? = null
        get() = when(valueId) {
            ITEM_DAILYDAY -> throw Exception("I am so dumb")
            else -> throw Exception("Illegal argument")
        }

    var valueId: Int = 0

    fun cycleThrough(state: Int, totalState: Int = 2): Int {
        return (state+1)%totalState
    }

    fun changeLayout() {
        valueId = cycleThrough(valueId, ITEMS)
    }


}