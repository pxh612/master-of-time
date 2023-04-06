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
        fun onUpdate_PickedDdGroupId_byDdEventListAdapter(groupId: Int?)
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

        when(position){
            pickedPosition -> holder.setSelected(true)
            else -> holder.setSelected(false)
        }

        /** Bad practice: find better solution for handling pickedPosition
         * 1. SharedPreferences
         * 2. ViewModel
         * */
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

            listener.onUpdate_PickedDdGroupId_byDdEventListAdapter(pickedGroupId)
        }
        holder.bind(item)
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
        fun setSelected(isSelected: Boolean){
            when(isSelected){
                true -> {
                    binding.name.setTextColor(Color.YELLOW)
                }
                false -> {
                    binding.name.setTextColor(Color.WHITE)
                }
            }
        }
    }


}



