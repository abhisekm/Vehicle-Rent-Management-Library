package com.abhisekm.vehiclerentmanagementlibrary.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.domain.Booking

@Entity(tableName = "rental_bike_table")
data class DatabaseBike(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var name: String,

    var image: String,

    @ColumnInfo(name = "background_color")
    var backgroundColor: String,

    var mileage: Int,

    @ColumnInfo(name = "start_price")
    var startPrice: Int,

    var torque: Int,

    @ColumnInfo(name = "charge_time")
    var chargeTime: Double
)

fun DatabaseBike.asDomainBikeModel(): Bike {
    return Bike(id, name, image, backgroundColor, mileage, startPrice, torque, chargeTime)
}

fun List<DatabaseBike>.asDomainBikeModel(): List<Bike> {
    return map {
        it.asDomainBikeModel()
    }
}


@Entity(tableName = "rental_booking_table")
data class DatabaseBooking(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "booking_id")
    var bookingId: Int,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,

    @ColumnInfo(name = "bike_id")
    var bikeId: Int,

    @ColumnInfo(name = "start_time")
    var startTime: Long,

    @ColumnInfo(name = "end_time")
    var endTime: Long,

    var amount: Double,

    @ColumnInfo(name = "bike_unlocked")
    var bikeUnlocked: Boolean = false,

    @Embedded
    var bike: DatabaseBike
)

fun DatabaseBooking.asDomainBookingModel(): Booking {
    return Booking(
        createdAt,
        bikeId,
        startTime,
        endTime,
        amount,
        bike.asDomainBikeModel(),
        bikeUnlocked,
        bookingId
    )
}

fun List<DatabaseBooking>.asDomainBookingModel(): List<Booking> {
    return map {
        it.asDomainBookingModel()
    }
}