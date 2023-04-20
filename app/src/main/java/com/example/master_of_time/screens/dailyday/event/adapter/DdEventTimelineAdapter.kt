package com.example.master_of_time.screens.dailyday.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.databinding.DdEventItemBinding
import com.example.master_of_time.databinding.DdEventItemGridLayoutBinding
import com.example.master_of_time.databinding.DdEventTimelimeRecyclerviewItemBinding
import timber.log.Timber


class DdEventTimelineAdapter(
    private var date: List<String>,
    private var calculationDistance: List<String>
) : RecyclerView.Adapter<DdEventTimelineAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DdEventTimelimeRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Timber.d("bind???")
        holder.bind(date[position], calculationDistance[position])
    }

    override fun getItemCount(): Int {
        date.forEach {
            Timber.d("date in recyclerView = $it")
        }
        Timber.d("size = ${date.size}")
        return 8
//        return date.size
    }

    class MyViewHolder(
        private val binding: DdEventTimelimeRecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(date: String, calculationDistance: String){
            binding.dateTextView.text = date
            binding.distanceTextView.text = calculationDistance
        }
    }
}
