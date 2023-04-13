package com.example.master_of_time.screens.dailyday.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.databinding.DdEventItemBinding
import com.example.master_of_time.screens.dailyday.event.DdEventListAdapter

class DdEventRecyclerViewAdapter(
    private var eventList: List<DdEvent>,
    private val onItemClicked: (DdEvent) -> Unit
): RecyclerView.Adapter<DdEventListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DdEventListAdapter.MyViewHolder {
        return DdEventListAdapter.MyViewHolder(
            DdEventItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: DdEventListAdapter.MyViewHolder, position: Int) {
        val item: DdEvent = eventList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun setList(list: List<DdEvent>) {
        eventList = list
    }
}

