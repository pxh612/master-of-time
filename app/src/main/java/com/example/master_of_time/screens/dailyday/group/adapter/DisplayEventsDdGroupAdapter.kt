package com.example.master_of_time.screens.dailyday.group

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DisplayEventsDdGroupItemBinding
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupDiffUtil


class DisplayEventsDdGroupAdapter(
    private val listener: Listener
): ListAdapter<DdGroup, DisplayEventsDdGroupAdapter.MyViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onUpdate_PickedDdGroupId_DdEventListAdapter(groupId: Int?)
    }

    var pickedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DisplayEventsDdGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        when(position){
            pickedPosition -> holder.binding.name.setTextColor(Color.YELLOW)
            else -> holder.binding.name.setTextColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener {
            val lastPickedPosition = pickedPosition
            var pickedGroupId: Int?

            when(position) {
                pickedPosition -> {
                    pickedPosition = null
                    pickedGroupId = null
                }
                else -> {
                    pickedPosition = position
                    pickedGroupId = item.id
                }
            }

            lastPickedPosition?.let { notifyItemChanged(it) }
            pickedPosition?.let { notifyItemChanged(it) }

            listener.onUpdate_PickedDdGroupId_DdEventListAdapter(pickedGroupId)
        }
    }


    class MyViewHolder(
        internal val binding: DisplayEventsDdGroupItemBinding,
        private val listener: Listener,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DdGroup) {

            binding.run{
                name.text = item.name
            }
        }
    }


}



