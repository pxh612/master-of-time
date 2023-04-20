package com.example.master_of_time.screens.dailyday.group

import android.view.LayoutInflater
import android.view.View
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
    private val viewModel: DdGroupViewModel,
    private val listener: Listener
): ListAdapter<DdGroup, PickDdGroupAdapter.MyViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onDdGroupPicked(isPicked: Boolean, item: DdGroup)
    }

    var selectedPosition: Int? = null

    private fun reselectPosition(newPosition: Int) {
        val lastSelectedPosition = selectedPosition
        selectedPosition = newPosition

        lastSelectedPosition?.let { if(it >= 0) notifyItemChanged(it) }
        newPosition.let { if(it >= 0) notifyItemChanged(it) }
    }

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
                    binding.isPicked.visibility = View.VISIBLE

                    // this is the problem?
                    adapter.selectedPosition = bindingAdapterPosition
                }
                else -> binding.isPicked.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                viewModel.run{
                    when(selectedGroupId){
                        ddGroup.id -> {
                            adapter.listener.onDdGroupPicked(false, ddGroup)
                            selectedGroupId = -1
                        }
                        else -> {
                            adapter.listener.onDdGroupPicked(true, ddGroup)
                            selectedGroupId = ddGroup.id
                        }
                    }
                }
                adapter.reselectPosition(bindingAdapterPosition)
            }
        }

    }



}



