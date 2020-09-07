package com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.BookingItemBinding
import com.abhisekm.vehiclerentmanagementlibrary.domain.Booking

class BookingHistoryAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Booking, BookingHistoryAdapter.BookingViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Booking>() {
        override fun areItemsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        return BookingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                BookingViewHolder.LAYOUT,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class BookingViewHolder(private val binding: BookingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.booking_item
        }

        fun bind(data: Booking, onClickListener: OnClickListener) {
            binding.booking = data
            binding.bike = data.bike
            binding.executePendingBindings()

            binding.btnLock.setOnClickListener {
                onClickListener.lock(data)
            }
            binding.btnUnlock.setOnClickListener {
                onClickListener.unlock(data)
            }
        }
    }

    class OnClickListener(val clickListener: (booking: Booking, unlock : Boolean) -> Unit) {
        fun lock(booking: Booking) = clickListener(booking, false)
        fun unlock(booking: Booking) = clickListener(booking, true)
    }
}