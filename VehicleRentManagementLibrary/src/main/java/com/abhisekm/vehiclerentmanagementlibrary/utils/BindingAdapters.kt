package com.abhisekm.vehiclerentmanagementlibrary.utils

import android.graphics.Color
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.adapter.BikesAdapter
import com.bumptech.glide.Glide


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Bike>?) {
    val adapter = recyclerView.adapter as BikesAdapter
    adapter.submitList(data)
}

@BindingAdapter("loadImage")
fun bindLoadImage(imageView: ImageView, url: String) {
    Glide.with(imageView).load(url).into(imageView)
}

@BindingAdapter("backgroundColor")
fun bindBackgroundColor(cardView: CardView, color: String) {
    val bgColor = if(color.isNotEmpty()) color else "#FFFFFF"
    cardView.setCardBackgroundColor(Color.parseColor(bgColor))
}
