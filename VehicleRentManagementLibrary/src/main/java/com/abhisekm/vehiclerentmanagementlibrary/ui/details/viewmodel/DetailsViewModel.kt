package com.abhisekm.vehiclerentmanagementlibrary.ui.details.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.data.DummyBikes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class DetailsViewModel (application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _data = MutableLiveData<Bike>()
    val data: LiveData<Bike>
        get() = _data

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}