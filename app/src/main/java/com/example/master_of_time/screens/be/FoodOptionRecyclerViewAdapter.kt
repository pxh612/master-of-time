package com.example.master_of_time.screens.be

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.databinding.FoodOptionRecyclerviewAdapterBinding
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewAdapter

class FoodOptionRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            FoodOptionRecyclerviewAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

    class MyViewHolder(
        private val binding: FoodOptionRecyclerviewAdapterBinding
    ): RecyclerView.ViewHolder(binding.root){

    }
}
