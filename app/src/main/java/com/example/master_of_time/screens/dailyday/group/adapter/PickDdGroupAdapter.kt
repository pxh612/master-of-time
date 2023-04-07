package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupPickerItemBinding
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupDiffUtil
import com.example.master_of_time.screens.dailyday.group.viewmodel.DdGroupViewModel
import timber.log.Timber



class PickDdGroupAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: DdGroupViewModel,
    private val listener: Listener
): ListAdapter<DdGroup, PickDdGroupAdapter.MyViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onDdGroupItemClick_atPickDdGroupAdapter(item: DdGroup)
    }

    var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdGroupPickerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            viewModel,
            this
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun notifyNewPosition(newPosition: Int) {
        selectedPosition?.let { notifyItemChanged(it) }
        notifyItemChanged(newPosition)
    }


    class MyViewHolder(
        internal val binding: DdGroupPickerItemBinding,
        private val viewModel: DdGroupViewModel,
        private val adapter: PickDdGroupAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ddGroup: DdGroup) {
            Timber.v("ddGroup.id = ${ddGroup.id} & viewModel.selectedGroupId = ${viewModel.selectedGroupId}")

            binding.run{
                title.text = ddGroup.name
            }

            when(ddGroup.id){
                viewModel.selectedGroupId -> {
                    binding.isPicked.text = "picked"
                    adapter.selectedPosition = bindingAdapterPosition
                }
                else -> binding.isPicked.text = ""
            }

            itemView.setOnClickListener {
                viewModel.selectedGroupId = ddGroup.id
                adapter.notifyNewPosition(bindingAdapterPosition)
                adapter.listener.onDdGroupItemClick_atPickDdGroupAdapter(ddGroup)
            }
        }

    }


}



