package com.abhisekm.vehiclerentmanagementlibrary.ui.main.viewmodel

import android.app.Application
import android.util.EventLog
import androidx.lifecycle.*
import com.abhisekm.vehiclerentmanagementlibrary.database.getDatabase
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.repository.RentalRepository
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.data.DummyBikes
import com.abhisekm.vehiclerentmanagementlibrary.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val rentalRepository = RentalRepository(database)

    val upcominBooking = rentalRepository.upcomingBooking
    val ongoingBooking = rentalRepository.ongoingBooking

    init {
        viewModelScope.launch {
            rentalRepository.updateBikeDB()
        }
    }

    val data = rentalRepository.bikes

    private val _map = MutableLiveData("https://images.fineartamerica.com/images-medium-large/london-england-street-map-michael-tompsett.jpg")
    val map: LiveData<String>
        get() = _map

    private val _eventNavigateToHistory = MutableLiveData<Event<Boolean>>()
    val eventNavigateToHistory : LiveData<Event<Boolean>>
    get() = _eventNavigateToHistory

    fun navigateToHistory(){
        _eventNavigateToHistory.value = Event(true)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}