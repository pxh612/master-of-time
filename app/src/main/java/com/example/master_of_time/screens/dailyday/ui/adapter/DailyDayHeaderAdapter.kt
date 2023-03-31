package com.example.master_of_time.screens.dailyday.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.databinding.AdapterDailyDayHeaderBinding
import timber.log.Timber

class DailyDayHeaderAdapter(
    private val onClick: (View) -> Unit
) : RecyclerView.Adapter<DailyDayHeaderAdapter.CustomViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        /** init viewHolder */
        val holder = CustomViewHolder(
            AdapterDailyDayHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        holder.binding.run {
            add.setOnClickListener { onClick(it) }
            layout.setOnClickListener{ onClick(it) }
        }
        return holder
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }


    override fun getItemCount(): Int = 1

    /** private class */

    class CustomViewHolder(
        internal val binding: AdapterDailyDayHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }



}


