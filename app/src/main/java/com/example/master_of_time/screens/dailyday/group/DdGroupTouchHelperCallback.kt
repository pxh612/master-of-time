package com.example.master_of_time.screens.dailyday.group

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupAdapter
import timber.log.Timber

class DdGroupTouchHelperCallback(
    private val adapter: DdGroupAdapter,
    dragDirs: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    swipeDirs: Int = 0
): ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    interface Contract {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
//        adapter.onRowMoved(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        return true
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Timber.w("onSwiped")
    }

}