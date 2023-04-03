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

class DailyDayAdapter(
    private val onAdapterClicked: (DdEvent) -> Unit
) : ListAdapter<DdEvent, DailyDayAdapter.DailyDayViewHolder>(DiffCallback) {

    /** init DiffCallback */
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DdEvent>() {

            override fun areItemsTheSame(oldItem: DdEvent, newItem: DdEvent) = (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: DdEvent, newItem: DdEvent) = (oldItem == newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDayViewHolder {
        /** init viewHolder */
        val holder = DailyDayViewHolder(
            ItemDailyDayBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: DailyDayViewHolder, position: Int) {
        val current: DdEvent = getItem(position)

        /** init Interaction */
        holder.itemView.setOnClickListener {
            onAdapterClicked(current)
        }

        holder.onBindCalled(current)
    }


    /** private class */
    class DailyDayViewHolder(
        private val binding: ItemDailyDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun onBindCalled(ddEvent: DdEvent) {
            binding.apply {
                title.text = ddEvent.title
                date.text = ddEvent.date.toOffsetDateTime().toDateFormat()
            }
        }
    }
    
}