package com.example.master_of_time.screens.dailyday.group

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DisplayEventsDdGroupItemBinding


class DisplayEventsDdGroupAdapter(
    private val listener: DisplayEventsDdGroupAdapterListener
): ListAdapter<DdGroup, DisplayEventsDdGroupAdapter.MyViewHolder>(MyDiffUtil()) {

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
        private val listener: DisplayEventsDdGroupAdapterListener,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DdGroup) {

            binding.run{
                name.text = item.name
            }
        }
    }


}

interface DisplayEventsDdGroupAdapterListener {
    fun onUpdate_PickedDdGroupId_DdEventListAdapter(groupId: Int?)
}

