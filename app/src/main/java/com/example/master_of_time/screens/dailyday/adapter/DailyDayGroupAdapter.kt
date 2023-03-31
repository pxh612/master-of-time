package com.example.master_of_time.screens.dailyday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.dailyday.DailyDay
import com.example.master_of_time.database.dailydaygroup.DailyDayGroup
import com.example.master_of_time.databinding.AdapterDailyDayGroupBinding

class DailyDayGroupAdapter(
    private val onAdapterClicked: (DailyDay) -> Unit
) : ListAdapter<DailyDayGroup, DailyDayGroupAdapter.CustomViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DailyDayGroup>() {

            override fun areItemsTheSame(oldItem: DailyDayGroup, newItem: DailyDayGroup) = (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: DailyDayGroup, newItem: DailyDayGroup) = (oldItem == newItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        /** init viewHolder */
        val holder = CustomViewHolder(
            AdapterDailyDayGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        return holder
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }


    override fun getItemCount(): Int = 1

    /** private class */

    class CustomViewHolder(
        internal val binding: AdapterDailyDayGroupBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(current: DailyDayGroup) {
            binding.run{
                title.text = current.title
                /** TODO: count (textView) */
            }
        }

    }



}


