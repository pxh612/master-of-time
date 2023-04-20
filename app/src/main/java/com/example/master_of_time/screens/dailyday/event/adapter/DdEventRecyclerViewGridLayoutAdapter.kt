package com.example.master_of_time.screens.dailyday.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.databinding.DdEventItemGridLayoutBinding
import com.example.master_of_time.module.dailyday.DdEventCalculation
import com.example.master_of_time.toDateFormat

class DdEventRecyclerViewGridLayoutAdapter (
    private var eventList: List<DdEvent>,
    private val listener: DdEventRecyclerViewAdapter.Listener
): RecyclerView.Adapter<DdEventRecyclerViewGridLayoutAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdEventItemGridLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: DdEvent = eventList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun setList(list: List<DdEvent>) {
        eventList = list
    }

    class MyViewHolder(
        private val binding: DdEventItemGridLayoutBinding,
        private val listener: DdEventRecyclerViewAdapter.Listener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DdEvent) {
            binding.apply {
                title.text = item.title

                val ddEventCalculation = DdEventCalculation(item.calculationTypeId, item.date)
                calculate.text = ddEventCalculation.displayDayDistanceFromPresent()
                searchedDate.text = ddEventCalculation.targetDate.toDateFormat()
            }

            itemView.setOnClickListener {
                listener.onDdEventItemClicked(item)
            }
        }
    }


}