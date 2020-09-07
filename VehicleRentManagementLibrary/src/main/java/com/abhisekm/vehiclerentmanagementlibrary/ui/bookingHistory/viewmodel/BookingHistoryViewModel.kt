package com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhisekm.vehiclerentmanagementlibrary.database.getDatabase
import com.abhisekm.vehiclerentmanagementlibrary.domain.Booking
import com.abhisekm.vehiclerentmanagementlibrary.repository.RentalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BookingHistoryViewModel(application: Application) :
    AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val rentalRepository = RentalRepository(database)

    val history : LiveData<List<Booking>> = rentalRepository.bookings

    private fun updateBooking(booking: Booking, unlocked: Boolean) {
        viewModelScope.launch {
            rentalRepository.updateBookingStatus(booking.id, unlocked)
        }
    }

    fun lock(booking: Booking) {
        updateBooking(booking, false)
    }

    fun unlock(booking: Booking) {
        updateBooking(booking, true)
    }

    class Factory(val app: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookingHistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BookingHistoryViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
