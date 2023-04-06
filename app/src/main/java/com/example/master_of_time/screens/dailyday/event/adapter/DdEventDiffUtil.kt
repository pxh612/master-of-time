package com.example.master_of_time.screens.dailyday.event.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.master_of_time.database.table.DdEvent

class DdEventDiffUtil: DiffUtil.ItemCallback<DdEvent>() {

    override fun areItemsTheSame(oldItem: DdEvent, newItem: DdEvent) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: DdEvent, newItem: DdEvent) = (oldItem == newItem)
}