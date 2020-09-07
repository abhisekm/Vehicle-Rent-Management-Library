package com.abhisekm.vehiclerentmanagementlibrary.ui.booking.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.vehiclerentmanagementlibrary.database.getDatabase
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.domain.Booking
import com.abhisekm.vehiclerentmanagementlibrary.domain.Trip
import com.abhisekm.vehiclerentmanagementlibrary.repository.RentalRepository
import com.abhisekm.vehiclerentmanagementlibrary.ui.details.data.DummyTripData
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.data.DummyBikes
import com.abhisekm.vehiclerentmanagementlibrary.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class BookingViewModel(val bikeId: Int, val tripId: Int, application: Application) :
    AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val rentalRepository = RentalRepository(database)

    private val _bikeData = MutableLiveData<Bike>()
    val bikeData: LiveData<Bike>
        get() = _bikeData

    private val _tripData = MutableLiveData<Trip>()
    val tripData: LiveData<Trip>
        get() = _tripData

    private val _startTime = MutableLiveData<Long>()
    val startTime: LiveData<Long>
        get() = _startTime

    private val _endTime = MutableLiveData<Long>()
    val endTime: LiveData<Long>
        get() = _endTime

    private val _total = MutableLiveData<Int>(0)
    val total: LiveData<Int>
        get() = _total

    init {
        _bikeData.value = DummyBikes.filter { it.id == bikeId }[0]
        _tripData.value = DummyTripData.filter { it.id == tripId }[0]
        calculateTotal()
    }

    private fun calculateTotal() {
        _total.value =
            if (_tripData.value?.duration != -1) _tripData.value?.discountedPrice ?: 0 else 0
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _eventShowStartPicker = MutableLiveData<Event<Long>>()
    val eventShowStartPicker: LiveData<Event<Long>>
        get() = _eventShowStartPicker

    private val _eventShowEndPicker = MutableLiveData<Event<Pair<Long, Long>>>()
    val eventShowEndPicker: LiveData<Event<Pair<Long, Long>>>
        get() = _eventShowEndPicker

    fun showStartDateTimePicker() {
        _eventShowStartPicker.value = Event(_startTime.value ?: 0)
    }

    fun showEndDateTimePicker() {
        if (tripData.value?.duration == -1)
            _eventShowEndPicker.value =
                Event(Pair<Long, Long>(_startTime.value ?: 0, _endTime.value ?: 0))
        else
            showStartDateTimePicker()
    }

    fun updateStartTime(timeInMillis: Long) {
        _startTime.value = applyOffset(timeInMillis)
        if (tripData.value?.duration != -1) {
            val endTime = when (tripData.value?.type) {
                "hour" -> timeInMillis + (tripData.value?.duration?.toLong()
                    ?.times(60 * 60 * 1000) ?: 0)
                "day" -> timeInMillis + (tripData.value?.duration?.toLong()
                    ?.times(24 * 60 * 60 * 1000) ?: 0)
                else -> timeInMillis
            }
            updateEndTime(endTime)
        } else {
            _endTime.value?.let {
                if (it < _startTime.value!!) {
                    _endTime.value = _startTime.value!!
                }
            }
        }
    }

    fun updateEndTime(timeInMillis: Long) {
        _endTime.value = applyOffset(timeInMillis)
        updateTotal()
    }

    private fun updateTotal() {
        if (_tripData.value?.duration == -1) {
            _tripData.value?.rate?.apply {
                val diff = _startTime.value?.let { _endTime.value?.minus(it) }
                val inHour: Long = diff?.div(60 * 60 * 1000) ?: 0
                val discount: Int = when (_tripData.value?.type) {
                    "hour" -> {
                        _total.value = this * inHour.toInt()
                        when (inHour.toInt()) {
                            in 1..6 -> 5
                            in 7..24 -> 10
                            else -> 0
                        }
                    }
                    "day" -> {
                        val inDay = inHour.div(24)
                        _total.value = this * inDay.toInt()
                        when (inDay.toInt()) {
                            in 1..6 -> 10
                            in 7..14 -> 15
                            in 15..29 -> 20
                            in 30..Int.MAX_VALUE -> 25
                            else -> 0
                        }
                    }
                    else -> 0
                }

                _total.value =
                    _tripData.value?.rate?.let { inHour.toInt() * (100 - discount) * it / 100 } ?: 0
            }
        }
    }

    private fun applyOffset(timeInMillis: Long): Long {
        val offset = (2 * 60 + 30) * 60 * 1000 //china to IST offset
        return timeInMillis + offset
    }

    private fun removeOffset(timeInMillis: Long): Long {
        val offset = 7 * 60 * 60 * 1000 //china to IST offset
        return timeInMillis - offset
    }

    private val _eventStartPayment = MutableLiveData<Event<String>>()
    val eventStartPayment: LiveData<Event<String>>
        get() = _eventStartPayment

    private val _eventErrorMessage = MutableLiveData<Event<String>>()
    val eventErrorMessage: LiveData<Event<String>>
        get() = _eventErrorMessage


    fun startPayment() {
        if (startTime.value == null || endTime.value == null) {
            _eventErrorMessage.value = Event("Select dates before continuing")
            return
        }

        if (_startTime.value!! > _endTime.value!!) {
            _eventErrorMessage.value = Event("Start Time cannot be after End Time")
            return
        }

        total.value?.let {
            _eventStartPayment.value = Event(getUPIString(it.toString()))
        }
    }

    private fun getUPIString(
        payeeAmount: String,
        payeeAddress: String = "9972367272@icici",
        payeeName: String = "Abhisek Majumder",
        payeeMCC: String = "AB1234",
        trxnID: String = "rentalBooking" + Calendar.getInstance().timeInMillis,
        trxnRefId: String = "bookingref",
        trxnNote: String = "rental booking",
        currencyCode: String = "INR",
        refUrl: String = "https://www.google.com"
    ): String {
        val upi = ("upi://pay?pa=" + payeeAddress + "&pn=" + payeeName
                + "&mc=" + payeeMCC + "&tid=" + trxnID + "&tr=" + trxnRefId
                + "&tn=" + trxnNote + "&am=" + payeeAmount + "&cu=" + currencyCode
                + "&refUrl=" + refUrl)
        return upi.replace(" ", "+")
    }

    fun getCurrentTime(): Long {
        return Calendar.getInstance().timeInMillis
    }

    private val _eventPaymentSuccess = MutableLiveData<Event<String>>()
    val eventPaymentSuccess: LiveData<Event<String>>
        get() = _eventPaymentSuccess

    fun paymentSuccess() {
        val booking = _startTime.value?.let { startTime ->
            _endTime.value?.let { endTime ->
                _total.value?.let { amount ->
                    bikeData.value?.let { bike ->
                        Booking(
                            Calendar.getInstance().timeInMillis, bikeId,
                            startTime, endTime, amount.toDouble(), bike
                        )
                    }
                }
            }
        }

        booking?.apply {
            viewModelScope.launch {
                rentalRepository.saveBooking(this@apply)
                _eventPaymentSuccess.value = Event("success")
            }
        }
    }

    class Factory(val bikeId: Int, val tripId: Int, val app: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BookingViewModel(bikeId, tripId, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}