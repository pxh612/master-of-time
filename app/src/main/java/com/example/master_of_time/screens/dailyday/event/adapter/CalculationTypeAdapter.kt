package com.example.master_of_time.screens.dailyday.event.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.databinding.CalculationTypeItemBinding
import com.example.master_of_time.module.dailyday.DdEventCalculationType
import timber.log.Timber

class CalculationTypeAdapter(
  private val list: List<DdEventCalculationType>,
  private val listener: Listener
) : RecyclerView.Adapter<CalculationTypeAdapter.MyViewHolder>() {

    interface Listener{
        fun onClickCalculationType(dateCalculationType: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder = MyViewHolder(
            CalculationTypeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            this
        )
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(
        private val binding: CalculationTypeItemBinding,
        private val adapter: CalculationTypeAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DdEventCalculationType) {

            binding.run{
                name.text = item.name
                description.text = item.description

                layout.setOnClickListener {
                    adapter.listener.onClickCalculationType(bindingAdapterPosition)
                }
            }

        }
    }



}
