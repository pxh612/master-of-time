package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.dailyday.DdEvent
import com.example.master_of_time.database.dailydaygroup.DdGroup
import com.example.master_of_time.databinding.ItemDailyDayGroupBinding

class DailyDayGroupAdapter(
    private val onAdapterClicked: (DdEvent) -> Unit
) : ListAdapter<DdGroup, DailyDayGroupAdapter.CustomViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DdGroup>() {

            override fun areItemsTheSame(oldItem: DdGroup, newItem: DdGroup) = (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: DdGroup, newItem: DdGroup) = (oldItem == newItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        /** init viewHolder */
        val holder = CustomViewHolder(
            ItemDailyDayGroupBinding.inflate(
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
        internal val binding: ItemDailyDayGroupBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(current: DdGroup) {
            binding.run{
                title.text = current.title
                /** TODO: count (textView) */
            }
        }

    }

}


