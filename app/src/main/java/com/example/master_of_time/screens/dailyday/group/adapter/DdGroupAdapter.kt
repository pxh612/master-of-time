package com.example.master_of_time.screens.dailyday.group.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupItemBinding
import com.example.master_of_time.screens.dailyday.group.DdGroupTouchHelperCallback
import com.example.master_of_time.screens.dailyday.group.viewmodel.DdGroupViewModel
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupAdapter.MyViewHolder
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class DdGroupAdapter(
    private val viewModel: DdGroupViewModel,
    internal val listener: Listener
) : ListAdapter<DdGroup, MyViewHolder>(DdGroupDiffUtil()), DdGroupTouchHelperCallback.Contract {

    interface Listener {
        fun onTitleClick(item: DdGroup)
        fun onDelete(item: DdGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            viewModel,
            listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        Timber.d("Moved $fromPosition to $toPosition")

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                val item = getItem(i)
                viewModel.updateGroup(item.copy( orderId = item.orderId*100))
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                val item = getItem(i)
                viewModel.updateGroup(item.copy( orderId = item.orderId*100))
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    class MyViewHolder(
        internal val binding: DdGroupItemBinding,
        private val viewModel: DdGroupViewModel,
        private val listener: Listener
        ) : RecyclerView.ViewHolder(binding.root) {

         fun bind(item: DdGroup) {

             viewModel.getDdEventCount_byGroupId(item.id).observe(itemView.context as LifecycleOwner) { countValue ->
                 binding.count.text = countValue.toString()
             }

             binding.run{
                 groupName.text = item.name
                 groupName.setOnClickListener { listener.onTitleClick(item) }
                 delete.setOnClickListener { listener.onDelete(item) }
             }
        }
    }




}


