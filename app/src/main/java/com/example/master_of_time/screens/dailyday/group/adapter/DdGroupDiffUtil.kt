package com.example.master_of_time.screens.dailyday.group.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.master_of_time.database.table.DdGroup

class DdGroupDiffUtil: DiffUtil.ItemCallback<DdGroup>() {

    override fun areItemsTheSame(oldItem: DdGroup, newItem: DdGroup) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: DdGroup, newItem: DdGroup) = (oldItem == newItem)

}