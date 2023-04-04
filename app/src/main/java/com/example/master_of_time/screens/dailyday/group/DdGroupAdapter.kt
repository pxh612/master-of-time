package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.dailydaygroup.DdGroup
import com.example.master_of_time.databinding.DdGroupItemBinding

class DdGroupAdapter(
    private val listener: DdGroupItemClickListener
) : ListAdapter<DdGroup, DdGroupAdapter.MyViewHolder>(MyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdGroupItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onItemClicK(item)
        }
        holder.bind(item)
    }

    class MyViewHolder(internal val binding: DdGroupItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DdGroup) {

            binding.run{
                title.text = item.name

            }

        }
    }

}

class MyDiffUtil: DiffUtil.ItemCallback<DdGroup>() {

    override fun areItemsTheSame(oldItem: DdGroup, newItem: DdGroup) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: DdGroup, newItem: DdGroup) = (oldItem == newItem)
}


