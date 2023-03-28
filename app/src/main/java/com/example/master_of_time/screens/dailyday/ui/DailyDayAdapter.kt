package com.example.master_of_time.screens.dailyday.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.dailyday.DailyDay

import com.example.master_of_time.databinding.AdapterDailyDayBinding
import com.example.master_of_time.toDateFormat
import com.example.master_of_time.toOffsetDateTime
import timber.log.Timber
import java.util.*

class DailyDayAdapter(
    private val onItemClicked: (DailyDay) -> Unit
) : ListAdapter<DailyDay, DailyDayAdapter.DailyDayViewHolder>(DiffCallback) {

    /** init DiffCallback */
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DailyDay>() {

            override fun areItemsTheSame(oldItem: DailyDay, newItem: DailyDay) = (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: DailyDay, newItem: DailyDay) = (oldItem == newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDayViewHolder {
        /** init viewHolder */
        val holder = DailyDayViewHolder(
            AdapterDailyDayBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: DailyDayViewHolder, position: Int) {
        val current = getItem(position)

        /** init Interaction */
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.onBindCalled(current)
    }


    /** private class */
    class DailyDayViewHolder(
        private val binding: AdapterDailyDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun onBindCalled(dailyDay: DailyDay) {
            Timber.v("> update ViewHolder")

            binding.apply {
                title.text = dailyDay.title
                date.text = dailyDay.date.toOffsetDateTime().toDateFormat()
            }
        }
    }
    
}