package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.dailydaygroup.DdGroup
import com.example.master_of_time.databinding.DdGroupPickerItemBinding

class DdGroupPickerAdapter(
    private val listener: DdGroupItemClickListener
) : ListAdapter<DdGroup, DdGroupPickerAdapter.MyViewHolder>(MyDiffUtil()) {

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
    }

    class MyViewHolder(
        internal val binding: DdGroupPickerItemBinding,
        private val listener: DdGroupItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DdGroup) {

            binding.run{
                title.text = item.name
                title.setOnClickListener {listener.onTitleClick(item)}
            }
        }
    }

}
