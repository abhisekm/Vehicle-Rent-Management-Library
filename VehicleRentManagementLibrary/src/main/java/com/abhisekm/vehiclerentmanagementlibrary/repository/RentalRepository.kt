package com.abhisekm.vehiclerentmanagementlibrary.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.abhisekm.vehiclerentmanagementlibrary.database.RentalDatabase
import com.abhisekm.vehiclerentmanagementlibrary.database.asDomainBikeModel
import com.abhisekm.vehiclerentmanagementlibrary.database.asDomainBookingModel
import com.abhisekm.vehiclerentmanagementlibrary.domain.*
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.data.DummyBikes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RentalRepository(private val database: RentalDatabase) {
    val bookings: LiveData<List<Booking>> =
        Transformations.map(database.bookingDao.getAllBooking()) {
            it.asDomainBookingModel()
        }

    val upcomingBooking : LiveData<Int> = database.bookingDao.getUpcomingBooking()
    val ongoingBooking : LiveData<Int> = database.bookingDao.getOngoingBooking()

    val bikes: LiveData<List<Bike>> =
        Transformations.map(database.bikeDao.getAllBikes()) {
            it.asDomainBikeModel()
        }

    suspend fun saveBooking(booking: Booking) {
        withContext(Dispatchers.IO) {
            database.bookingDao.insert(booking.asDatabaseModule())
            Log.d("Booking", "saveBooking: completed")
        }
    }

    suspend fun getBikeDetails(id: Int): Bike {
        return withContext(Dispatchers.IO) {
            val bike = database.bikeDao.getBike(id)
            return@withContext bike.asDomainBikeModel()
        }
    }

    suspend fun updateBikeDB() {
        withContext(Dispatchers.IO) {
            val count = database.bikeDao.getAllBikesCount()
            if (count == 0) {
                database.bikeDao.insert(DummyBikes.asDatabaseBikeModule())
            }
        }
    }

    suspend fun updateBookingStatus(id: Int, bikeUnlocked: Boolean) {
        withContext(Dispatchers.IO){
            database.bookingDao.updateBikeStatus(id, bikeUnlocked)
        }
    }
}