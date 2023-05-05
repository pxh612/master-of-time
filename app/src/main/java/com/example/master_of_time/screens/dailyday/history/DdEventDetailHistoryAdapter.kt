package com.example.master_of_time.screens.dailyday.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.Logger
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.table.DdEventHistory
import com.example.master_of_time.databinding.DdEventDetailHistoryItemBinding
import com.example.master_of_time.databinding.DdEventItemBinding
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapter
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupDiffUtil
import com.example.master_of_time.toDateFormat
import com.example.master_of_time.toEpochDay
import timber.log.Timber

class DdEventDetailHistoryAdapter(
    private val listener: Listener
) : ListAdapter<DdEventHistory, DdEventDetailHistoryAdapter.MyViewHolder>(DdEventHistoryDiffUtil())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdEventDetailHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface Listener{
        fun onDdEventHistoryClicked(ddEventHistory: DdEventHistory)
    }

    class MyViewHolder(
        private val binding: DdEventDetailHistoryItemBinding,
        private val listener: Listener
    ): RecyclerView.ViewHolder(binding.root)  {

        fun bind(ddEventHistory: DdEventHistory){
            Timber.d("testing epochDay: ${ddEventHistory.date.toEpochDay()}")
            Logger.d("testing epochDay: ${ddEventHistory.date.toEpochDay()}")

            binding.run {
                dateInfo.text = ddEventHistory.date.toDateFormat()
                titleTextView.text = ddEventHistory.title
                descriptionTextView.text = ddEventHistory.description
            }
            itemView.setOnClickListener {
                listener.onDdEventHistoryClicked(ddEventHistory)
            }
        }
    }
}

class DdEventHistoryDiffUtil: DiffUtil.ItemCallback<DdEventHistory>() {

    override fun areItemsTheSame(oldItem: DdEventHistory, newItem: DdEventHistory) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: DdEventHistory, newItem: DdEventHistory) = (oldItem == newItem)

}