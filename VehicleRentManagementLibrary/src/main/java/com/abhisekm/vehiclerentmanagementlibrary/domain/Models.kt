package com.abhisekm.vehiclerentmanagementlibrary.domain

data class Bike(
    var id : Int,
    var name: String,
    var image: String,
    var backgroundColor: String,
    var mileage: Int,
    var startPrice: Int,
    var torque: Int,
    var chargeTime: Double

)

data class Trip(
    var id: Int,
    var type: String,
    var rate: Int,
    var discount: Int,
    var bestSeller: Boolean,
    var duration: Int
){
    val totalPrice : Int = if (duration == -1) -1 else when(type){
        "hour" -> rate * duration
        "day" -> rate * 24 * duration
        else -> 0
    }

    val discountedPrice : Int = if(totalPrice == -1 || discount == 0) -1 else totalPrice * (100-discount) / 100
}