package com.abhisekm.vehiclerentmanagementlibrary.domain

import androidx.room.ColumnInfo
import com.abhisekm.vehiclerentmanagementlibrary.database.DatabaseBike
import com.abhisekm.vehiclerentmanagementlibrary.database.DatabaseBooking
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

data class Bike(
    var id: Int,
    var name: String,
    var image: String,
    var backgroundColor: String,
    var mileage: Int,
    var startPrice: Int,
    var torque: Int,
    var chargeTime: Double

)

fun Bike.asDatabaseBikeModel(): DatabaseBike {
    return DatabaseBike(id, name, image, backgroundColor, mileage, startPrice, torque, chargeTime)
}

fun List<Bike>.asDatabaseBikeModule(): List<DatabaseBike> {
    return map {
        it.asDatabaseBikeModel()
    }
}

data class Trip(
    var id: Int,
    var type: String,
    var rate: Int,
    var discount: Int,
    var bestSeller: Boolean,
    var duration: Int
) {
    val totalPrice: Int = if (duration == -1) -1 else when (type) {
        "hour" -> rate * duration
        "day" -> rate * 24 * duration
        else -> 0
    }

    val discountedPrice: Int =
        if (totalPrice == -1 || discount == 0) -1 else totalPrice * (100 - discount) / 100
}

data class Booking(
    var createdAt: Long,
    var bikeId: Int,
    var startTime: Long,
    var endTime: Long,
    var amount: Double,
    var bike: Bike,
    var bikeUnlocked: Boolean = false,
    var id: Int = 0,
) {
    val startDate: String =
        SimpleDateFormat("dd/MM/YYYY HH:mm", Locale.ENGLISH).format(Date(startTime))

    val endDate: String =
        SimpleDateFormat("dd/MM/YYYY HH:mm", Locale.ENGLISH).format(Date(endTime))

    val ongoing: Boolean = Calendar.getInstance().timeInMillis in startTime..endTime
}

fun Booking.asDatabaseModule(): DatabaseBooking {
    return DatabaseBooking(
        id,
        createdAt,
        bikeId,
        startTime,
        endTime,
        amount,
        bikeUnlocked,
        bike.asDatabaseBikeModel()
    )
}
