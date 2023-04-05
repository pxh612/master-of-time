package com.example.master_of_time.screens.dailyday.group

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
    }


    class MyViewHolder(
        internal val binding: DisplayEventsDdGroupItemBinding,
        private val listener: DisplayEventsDdGroupAdapterListener,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DdGroup) {

            binding.run{
                title.text = item.name
                itemView.setOnClickListener { listener.onItemClick(item) }
            }

        }
    }


}

interface DisplayEventsDdGroupAdapterListener {
    fun onItemClick(item: DdGroup)
}

