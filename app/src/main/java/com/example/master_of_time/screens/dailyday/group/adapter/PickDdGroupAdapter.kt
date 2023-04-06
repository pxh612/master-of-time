package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupPickerItemBinding
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupDiffUtil


class PickDdGroupAdapter(
    private val listener: Listener
): ListAdapter<DdGroup, PickDdGroupAdapter.MyViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onItemClick(item: DdGroup)
    }

    var pickedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdGroupPickerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        when(position){
            pickedPosition -> holder.binding.isPicked.text = "picked"
            else -> holder.binding.isPicked.text = ""
        }

        holder.itemView.setOnClickListener {
            pickedPosition?.let { notifyItemChanged(it) }
            pickedPosition = position
            pickedPosition?.let { notifyItemChanged(it) }

            listener.onItemClick(item)
        }
    }


    class MyViewHolder(
        internal val binding: DdGroupPickerItemBinding,
        private val listener: Listener,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DdGroup) {

            binding.run{
                title.text = item.name

            }

        }
    }
}



