package com.abhisekm.vehiclerentmanagementlibrary.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.TripItemBinding
import com.abhisekm.vehiclerentmanagementlibrary.domain.Trip

class TripAdapter(private val onClickListener: OnClickListener) : ListAdapter<Trip, TripAdapter.TripViewHolder>(DiffCallback){
    var productColor: String = "#000"

    companion object DiffCallback : DiffUtil.ItemCallback<Trip>(){
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
           return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                TripViewHolder.LAYOUT,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position), productColor, onClickListener)
    }

    class TripViewHolder (private val binding: TripItemBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.trip_item
        }

        fun bind(data: Trip, productColor: String, onClickListener: OnClickListener) {
            binding.trip = data
            binding.productColor = productColor
            binding.rootCard.setOnClickListener { onClickListener.onClick(data) }
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (bikeData: Trip) -> Unit) {
        fun onClick(bikeData: Trip) = clickListener(bikeData)
    }
}