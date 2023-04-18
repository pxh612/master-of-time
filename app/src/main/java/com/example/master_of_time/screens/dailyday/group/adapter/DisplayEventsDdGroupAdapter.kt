package com.example.master_of_time.screens.dailyday.group

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.R
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DisplayEventsDdGroupItemBinding
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupDiffUtil


class DisplayEventsDdGroupAdapter(
    private val listener: Listener,
    internal val viewModel: DdEventViewModel
): ListAdapter<DdGroup, DisplayEventsDdGroupAdapter.MyViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onGroupDisplayClick(groupId: Long?)
    }

    private var lastAdapterPosition
        get() = viewModel.lastSelectedAdapterPosition
        set(value) { viewModel.lastSelectedAdapterPosition = value }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DisplayEventsDdGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            this
        )
    }

    private fun notifyItemReselection(newSelectionAdapterPosition: Int) {
        if(lastAdapterPosition >= 0) notifyItemChanged(lastAdapterPosition)
        notifyItemChanged(newSelectionAdapterPosition)

        lastAdapterPosition = newSelectionAdapterPosition
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class MyViewHolder(
        internal val binding: DisplayEventsDdGroupItemBinding,
        private val adapter: DisplayEventsDdGroupAdapter
    ) : RecyclerView.ViewHolder(binding.root) {


        private var selectedGroupId: Long
            get() = adapter.viewModel.selectedGroupId
            set(value){ adapter.viewModel.selectedGroupId = value }


        fun bind(groupItem: DdGroup) {
            binding.run{
                name.text = " ${groupItem.name} "

                displaySelected(groupItem.id == selectedGroupId)

                itemView.setOnClickListener {
                    selectedGroupId = when(groupItem.id){
                        selectedGroupId -> -1L
                        else -> groupItem.id
                    }
                    adapter.notifyItemReselection(bindingAdapterPosition)
                    adapter.listener.onGroupDisplayClick(selectedGroupId)
                }
            }
        }

        private fun displaySelected(isSelected: Boolean) {
            binding.run {
                when (isSelected) {
                    true -> {
                        name.setTextColor(Color.WHITE)
                        name.setBackgroundResource(R.drawable.round_corner_button)
                    }
                    false -> {
                        name.setTextColor(Color.BLACK)
                        name.setBackgroundResource(R.drawable.round_corner_button_white)
                    }
                }
            }
        }
    }




}



