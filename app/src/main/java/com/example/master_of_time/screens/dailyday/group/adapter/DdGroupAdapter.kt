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
import com.example.master_of_time.screens.dailyday.group.DdGroupViewModel
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupAdapter.MyViewHolder
import kotlinx.coroutines.*
import timber.log.Timber

class DdGroupAdapter(
    private val viewModel: DdGroupViewModel,
    private val listener: Listener
) : ListAdapter<DdGroup, MyViewHolder>(DdGroupDiffUtil()) {

    interface Listener {
        fun onTitleClick(item: DdGroup)
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

    class MyViewHolder(
        internal val binding: DdGroupItemBinding,
        private val viewModel: DdGroupViewModel,
        private val listener: Listener
        ) : RecyclerView.ViewHolder(binding.root) {

         @SuppressLint("SetTextI18n")
         fun bind(item: DdGroup) {

             viewModel.getDdEventCount_byGroupId(item.id).observe(itemView.context as LifecycleOwner, Observer { count ->
                 binding.groupName.text = "${item.name} ($count)"
             })

             binding.run{
                 groupName.text = item.name
                 groupName.setOnClickListener { listener.onTitleClick(item) }
             }

             Timber.d("Finish bind ${item.name}.")
        }
    }


}


