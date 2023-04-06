package com.example.master_of_time.screens.dailyday.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdEvent

import com.example.master_of_time.databinding.ItemDailyDayBinding
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventDiffUtil
import com.example.master_of_time.toDateFormat

class DdEventListAdapter(
    private val onItemClicked: (DdEvent) -> Unit
) : ListAdapter<DdEvent, DdEventListAdapter.DailyDayViewHolder>(DdEventDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDayViewHolder {
        val holder = DailyDayViewHolder(
            ItemDailyDayBinding.inflate(
                LayoutInflater.from( parent.context), parent, false
            ),
            onItemClicked
        )
        return holder
    }

    override fun onBindViewHolder(holder: DailyDayViewHolder, position: Int) {
        val item: DdEvent = getItem(position)
        holder.bind(item)
    }


    class DailyDayViewHolder(
        private val binding: ItemDailyDayBinding,
        private val onItemClicked: (DdEvent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: DdEvent) {
            binding.apply {
                title.text = item.title
                date.text = item.date.toDateFormat()
            }

            itemView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
    
}

