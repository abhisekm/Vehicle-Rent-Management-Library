package com.abhisekm.vehiclerentmanagementlibrary.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.BikeItemBinding
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike

class BikesAdapter(private val onClickListener: OnClickListener) : ListAdapter<Bike, BikesAdapter.BikeViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Bike>(){
        override fun areItemsTheSame(oldItem: Bike, newItem: Bike): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bike, newItem: Bike): Boolean {
           return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeViewHolder {
        return BikeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                BikeViewHolder.LAYOUT,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BikeViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class BikeViewHolder (private val binding: BikeItemBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.bike_item
        }

        fun bind(data: Bike, onClickListener: OnClickListener) {
            binding.bikeData = data
            binding.btnBook.setOnClickListener { onClickListener.onClick(data) }
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (bikeData: Bike) -> Unit) {
        fun onClick(bikeData: Bike) = clickListener(bikeData)
    }
}