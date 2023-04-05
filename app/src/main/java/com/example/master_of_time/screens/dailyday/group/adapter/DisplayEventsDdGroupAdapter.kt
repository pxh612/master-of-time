package com.example.master_of_time.screens.dailyday.group

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.R
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
            pickedPosition?.let { notifyItemChanged(it) }
            pickedPosition = position
            pickedPosition?.let { notifyItemChanged(it) }
        }
    }


    class MyViewHolder(
        internal val binding: DisplayEventsDdGroupItemBinding,
        private val listener: DisplayEventsDdGroupAdapterListener,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DdGroup) {

            binding.run{
                name.text = item.name
                itemView.setOnClickListener { listener.onDdGroupItemClick(item) }
            }

        }
    }


}

interface DisplayEventsDdGroupAdapterListener {
    fun onDdGroupItemClick(item: DdGroup)
}

