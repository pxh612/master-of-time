package com.example.master_of_time.screens.dailyday.event

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewAdapter
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewGridLayoutAdapter


class DdEventLayoutWrapper(
    private val context: Context,
    private val listener: DdEventRecyclerViewAdapter.Listener,
    private val viewModel: DdEventViewModel
) {

    companion object{
        public val LAYOUT_LINEAR = 0
        val LAYOUT_GRID = 1
        private val LAYOUTS = 2

        private val SPAN_COUNT = 3
    }

    val adapterLinear = DdEventRecyclerViewAdapter(emptyList(), listener, viewModel)
    val adapterGrid = DdEventRecyclerViewGridLayoutAdapter(emptyList(), listener)



    val layoutManager: LayoutManager
        get() = when(mLayoutState) {
            LAYOUT_LINEAR -> LinearLayoutManager(context)
            LAYOUT_GRID -> GridLayoutManager(context, SPAN_COUNT)
            else -> throw Exception("Illegal argument for layout")
        }


    var mLayoutState: Int = LAYOUT_LINEAR

    fun cycleThrough(state: Int, totalState: Int = 2): Int {
        return (state+1)%totalState
    }

    fun circleLayout() {
        mLayoutState = cycleThrough(mLayoutState, LAYOUTS)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<DdEvent>) {
        adapterLinear.setList(list)
        adapterLinear.notifyDataSetChanged()

        adapterGrid.setList(list)
        adapterGrid.notifyDataSetChanged()
    }
}
