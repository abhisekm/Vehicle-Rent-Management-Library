package com.abhisekm.vehiclerentmanagementlibrary.ui.details.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.vehiclerentmanagementlibrary.database.getDatabase
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.domain.Trip
import com.abhisekm.vehiclerentmanagementlibrary.repository.RentalRepository
import com.abhisekm.vehiclerentmanagementlibrary.ui.details.data.DummyTripData
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.data.DummyBikes
import kotlinx.coroutines.*

class DetailsViewModel(val id: Int, application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val database = getDatabase(application)
    private val rentalRepository = RentalRepository(database)

    private val _data = MutableLiveData<Bike>()
    val data: LiveData<Bike>
        get() = _data

    private val _shortTrip = MutableLiveData<List<Trip>>(DummyTripData.filter { it.type == "hour" })
    val shortTrip: LiveData<List<Trip>>
        get() = _shortTrip


    private val _longTrip = MutableLiveData<List<Trip>>(DummyTripData.filter { it.type == "day" })
    val longTrip: LiveData<List<Trip>>
        get() = _longTrip


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val bike = rentalRepository.getBikeDetails(id)
                withContext(Dispatchers.Main) {
                    _data.value = bike
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val id: Int, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(id, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}