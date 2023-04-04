package com.example.master_of_time.screens.dailyday.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.dailyday.DdEvent

import com.example.master_of_time.databinding.ItemDailyDayBinding
import com.example.master_of_time.toDateFormat
import com.example.master_of_time.toOffsetDateTime

class DdEventAdapter(
    private val onItemClicked: (DdEvent) -> Unit
) : ListAdapter<DdEvent, DdEventAdapter.DailyDayViewHolder>(MyDiffUtil()) {


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
                date.text = item.date.toOffsetDateTime().toDateFormat()
            }

            itemView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
    
}

class MyDiffUtil: DiffUtil.ItemCallback<DdEvent>() {

    override fun areItemsTheSame(oldItem: DdEvent, newItem: DdEvent) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: DdEvent, newItem: DdEvent) = (oldItem == newItem)
}

