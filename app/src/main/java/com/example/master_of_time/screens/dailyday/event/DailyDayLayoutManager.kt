package com.example.master_of_time.screens.dailyday.event

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager


class DailyDayLayoutManager(
    private val context: Context
) {

    companion object{
        private val LAYOUT_LINEAR = 0
        private val LAYOUT_GRID = 1
        private val LAYOUTS = 2

        private val SPAN_COUNT = 3
    }

    var value: LayoutManager? = null
        get() = when(layout) {
            LAYOUT_LINEAR -> LinearLayoutManager(context)
            LAYOUT_GRID -> GridLayoutManager(context, SPAN_COUNT)
            else -> throw Exception("Illegal argument for layout")
        }


    private var layout: Int = LAYOUT_LINEAR

    fun cycleThrough(state: Int, totalState: Int = 2): Int {
        return (state+1)%totalState
    }

    fun changeLayout() {
        layout = cycleThrough(layout, LAYOUTS)
    }
}
