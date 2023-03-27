package com.example.master_of_time.screens.dailyday.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.R
import com.example.master_of_time.database.dailyday.DailyDay
import com.example.master_of_time.databinding.AdapterDailyDayBinding
import com.example.master_of_time.databinding.FragmentDailyDayBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class DailyDayAdapter (
//    private val onItemClicked: (DailyDay) -> Unit
) : ListAdapter<DailyDay, DailyDayAdapter.DailyDayViewHolder>(DiffCallback) {

    /** init DiffCallback */
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DailyDay>() {
            override fun areItemsTheSame(oldItem: DailyDay, newItem: DailyDay): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DailyDay, newItem: DailyDay): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDayViewHolder {
        /** init viewHolder */
        val viewHolder = DailyDayViewHolder(
            AdapterDailyDayBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )

        /** init ViewHolder's onClickListener */
        // Nothing for now

//        viewHolder.itemView.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            onItemClicked(getItem(position))
//        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: DailyDayViewHolder, position: Int) {
        holder.onBindCalled(getItem(position))
    }


    /** private class */
    class DailyDayViewHolder(
        private val binding: AdapterDailyDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBindCalled(dailyDay: DailyDay) {
            Timber.v("> update ViewHolder")
            binding.content.text = dailyDay.title
        }
    }

}

// === Class declaration: vanilla setup with RecyclerView

//class DailyDayAdapter(
//    private val context: Context,
//    private val dataset: List<DailyDay>
//    ) : RecyclerView.Adapter<DailyDayAdapter.DailyDayViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDayViewHolder {
//        // The onCreateViewHolder() method is called by the layout manager to create new view holders
//        // for the RecyclerView (when there are no existing view holders that can be reused).
//        // Remember that a view holder represents a single list item view.
//
//        val adapterLayout = LayoutInflater.from(parent.context)
//            .inflate(R.layout.adapter_daily_day, parent, false)
//
//        return DailyDayViewHolder(adapterLayout)
//    }
//
//    override fun onBindViewHolder(holder: DailyDayViewHolder, position: Int) {
//        // The last method you need to override is onBindViewHolder().
//        // This method is called by the layout manager in order to replace the contents of a list item view.
//
//        val dailyDay: DailyDay = dataset[position]
//        holder.textView.text = dailyDay.title
//        // pxh612: can I just do this?
//        // Instead of: holder.textView.text = context.resources.getString(item.stringResourceId)
//
//
//    }
//    override fun getItemCount() = dataset.size
//
//
//    /** private class */
//    class DailyDayViewHolder(
//        private val view: View
//    ) : RecyclerView.ViewHolder(view) {
//
//        val textView: TextView = view.findViewById(R.id.add)
//    }
//}