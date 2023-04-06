package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupItemBinding
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupDiffUtil
import com.example.master_of_time.screens.dailyday.group.DdGroupAdapter.DdGroupAdapterViewHolder as DdGroupAdapterViewHolder

class DdGroupAdapter(
    private val viewModel: DdGroupViewModel,
    private val listener: Listener
) : ListAdapter<DdGroup, DdGroupAdapterViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onTitleClick(item: DdGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DdGroupAdapterViewHolder {
        return DdGroupAdapterViewHolder(
            DdGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            viewModel,
            listener
        )
    }

    override fun onBindViewHolder(holder: DdGroupAdapterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DdGroupAdapterViewHolder(
        internal val binding: DdGroupItemBinding,
        private val viewModel: DdGroupViewModel,
        private val listener: Listener
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DdGroup) {

            binding.run{
//                viewModel.getDdEventCount_byGroupId(item.id).observe()
                groupName.setOnClickListener { listener.onTitleClick(item) }
            }

//            viewModel.getGroupName(ddEvent.groupId)?.observe(viewLifecycleOwner) {
//                ddGroupPicker.text = it
//            }
//            viewModel.displayGroupName_DdGroupItem(item)
        }
    }


}


