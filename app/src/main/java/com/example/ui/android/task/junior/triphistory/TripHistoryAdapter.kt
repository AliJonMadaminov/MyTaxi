package com.example.ui.android.task.junior.triphistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.android.task.junior.databinding.DateTextLayoutBinding
import com.example.ui.android.task.junior.databinding.ItemTripHistoryBinding
import com.example.ui.android.task.junior.models.trip.BaseTrip
import com.example.ui.android.task.junior.utils.sameDay

private val ITEM_VIEW_TYPE_DATE = 0
private val ITEM_VIEW_TYPE_TRIP = 1

class TripHistoryAdapter(val clickListener: TripHistoryListener) : ListAdapter<DataItem,
        RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    fun submitTrips(trips: List<BaseTrip>) {
        val items = mutableListOf<DataItem>()
        trips.forEachIndexed { index, baseTrip ->
            val currentItemStartTime = baseTrip.generalTripInfo.tripStartTime
            if (index == 0) {
                items.add(DataItem.DateItem(currentItemStartTime))
                items.add(DataItem.TripHistoryItem(baseTrip))
            } else if (sameDay(
                    currentItemStartTime,
                    trips[index - 1].generalTripInfo.tripStartTime
                )
            ) {
                items.add(DataItem.TripHistoryItem(baseTrip))
            } else {
                items.add(DataItem.DateItem(baseTrip.generalTripInfo.tripStartTime))
                items.add(DataItem.TripHistoryItem(baseTrip))
            }
        }
        submitList(items)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TripViewHolder -> {
                val tripItem = getItem(position) as DataItem.TripHistoryItem
                holder.bind(clickListener, tripItem)
            }

            is DateTextViewHolder -> {
                val dateItem = getItem(position) as DataItem.DateItem
                holder.bind(dateItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_DATE -> DateTextViewHolder.from(parent)
            ITEM_VIEW_TYPE_TRIP -> TripViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    class DateTextViewHolder(val binding: DateTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dateItem: DataItem.DateItem) {
            binding.dateInMillis = dateItem.dateInMillis
        }

        companion object {
            fun from(parent: ViewGroup): DateTextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DateTextLayoutBinding.inflate(layoutInflater, parent, false)
                return DateTextViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.DateItem -> ITEM_VIEW_TYPE_DATE
            is DataItem.TripHistoryItem -> ITEM_VIEW_TYPE_TRIP
        }
    }

    class TripViewHolder private constructor(val binding: ItemTripHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: TripHistoryListener, tripDataItem: DataItem.TripHistoryItem) {
            binding.trip = tripDataItem.baseTrip
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TripViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTripHistoryBinding.inflate(layoutInflater, parent, false)

                return TripViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class TripHistoryListener(val clickListener: (orderNumber: Int) -> Unit) {
    fun onClick(trip: BaseTrip) = clickListener(trip.generalTripInfo.orderNumber)
}

sealed class DataItem {
    data class TripHistoryItem(val baseTrip: BaseTrip) : DataItem() {
        override val id = baseTrip.generalTripInfo.orderNumber
    }

    class DateItem(val dateInMillis: Long) : DataItem() {
        override val id = Int.MAX_VALUE
    }

    abstract val id: Int
}