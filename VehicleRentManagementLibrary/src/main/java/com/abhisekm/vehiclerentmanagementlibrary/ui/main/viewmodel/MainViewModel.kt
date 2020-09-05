package com.abhisekm.vehiclerentmanagementlibrary.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.vehiclerentmanagementlibrary.domain.Bike
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.data.DummyBikes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _data = MutableLiveData(DummyBikes)
    val data: LiveData<List<Bike>>
        get() = _data

    private val _map = MutableLiveData("https://images.fineartamerica.com/images-medium-large/london-england-street-map-michael-tompsett.jpg")
    val map: LiveData<String>
        get() = _map

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