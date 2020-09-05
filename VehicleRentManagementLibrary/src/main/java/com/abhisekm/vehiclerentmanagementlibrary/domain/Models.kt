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