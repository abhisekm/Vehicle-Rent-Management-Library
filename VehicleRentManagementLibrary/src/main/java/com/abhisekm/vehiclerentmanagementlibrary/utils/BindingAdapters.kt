package com.abhisekm.vehiclerentmanagementlibrary.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Paint
import android.text.format.DateFormat
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.domain.Trip
import com.abhisekm.vehiclerentmanagementlibrary.ui.details.adapter.TripAdapter
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.adapter.BikesAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Bike>?) {
    val adapter = recyclerView.adapter as BikesAdapter
    adapter.submitList(data)
}

@BindingAdapter("listTrip")
fun bindRecyclerViewTrip(recyclerView: RecyclerView, data: List<Trip>?) {
    val adapter = recyclerView.adapter as TripAdapter
    adapter.submitList(data)
}

@BindingAdapter("tripColorTheme")
fun bindRecyclerViewColor(recyclerView: RecyclerView, productColor: String?) {
    val adapter = recyclerView.adapter as TripAdapter
    adapter.productColor = productColor ?: "#000"
}

@BindingAdapter("loadImage")
fun bindLoadImage(imageView: ImageView, url: String?) {
    url?.let { Glide.with(imageView).load(it).into(imageView) }
}

@BindingAdapter("backgroundColor")
fun bindBackgroundColor(cardView: CardView, color: String) {
    val bgColor = if (color.isNotEmpty()) color else "#FFFFFF"
    cardView.setCardBackgroundColor(Color.parseColor(bgColor))
}

@BindingAdapter("bgColor")
fun bindViewBackgroundColor(view: View, color: String) {
    val bgColor = if (color.isNotEmpty()) color else "#FFFFFF"
    view.setBackgroundColor(Color.parseColor(bgColor))
}

@BindingAdapter("textColorHex")
fun bindTextColor(textView: TextView, color: String) {
    val bgColor = if (color.isNotEmpty()) color else "#FFFFFF"
    textView.setTextColor(Color.parseColor(bgColor))
}

@BindingAdapter("strike")
fun bindStrike(textView: TextView, strike: Boolean) {
    if (strike) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("isDiscounted")
fun bindIsDiscounted(textView: TextView, isDiscounted: Boolean) {
    textView.visibility = if (isDiscounted) View.VISIBLE else View.GONE
}

@BindingAdapter("amount")
fun bindAmount(textView: TextView, amount: Int) {
    textView.visibility = if (amount <= 0) View.GONE else View.VISIBLE
    textView.text = "Rs $amount"
}

@BindingAdapter("amountShow")
fun bindAmountDontHide(textView: TextView, amount: Int) {
    textView.text = "Rs $amount"
}

@BindingAdapter("tripDuration")
fun bindTextViewTripDuration(textView: TextView, amount: Int) {
    textView.text = if (amount == -1) "Custom" else amount.toString()
}

@BindingAdapter("showTime")
fun bindTextViewShowTime(textView: TextView, timeInMillis: Long?) {
    textView.text = "Click here to select date"
    timeInMillis?.apply {
        textView.text = DateFormat.format("dd-MM-yyyy HH:mm", this).toString()
    }
}

