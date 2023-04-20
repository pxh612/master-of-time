package com.example.master_of_time.screens.dailyday.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.databinding.DdEventItemBinding
import com.example.master_of_time.module.dailyday.DdEventCalculation
import com.example.master_of_time.toDateFormat

class DdEventRecyclerViewAdapter(
    private var eventList: List<DdEvent>,
    private val listener: Listener
): RecyclerView.Adapter<DdEventRecyclerViewAdapter.MyViewHolder>() {

    interface Listener{
        fun onDdEventItemClicked(ddEvent: DdEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdEventItemBinding.inflate(
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
        private val binding: DdEventItemBinding,
        private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DdEvent) {
            binding.apply {
                title.text = item.title

                val ddEventCalculation = DdEventCalculation(item.calculationTypeId, item.date)
                date.text = when(item.calculationTypeId){
                    DdEventCalculation.ONE_TIME.id -> ""
                    else -> ddEventCalculation.displayPickedDate()
                }

                val targetDate = ddEventCalculation.targetDate
                calculate.text = DdEventCalculation(targetDate).displayDayDistanceFromPresent()
                searchedDate.text = targetDate.toDateFormat()
            }

            itemView.setOnClickListener { listener.onDdEventItemClicked(item) }
        }
    }
}

