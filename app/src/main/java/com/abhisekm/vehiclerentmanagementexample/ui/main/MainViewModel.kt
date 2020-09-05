package com.abhisekm.vehiclerentmanagementexample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var _navigateToModule = MutableLiveData<Boolean>(false)
    val navigateToModule: LiveData<Boolean>
        get() = _navigateToModule

    fun navigate(){
        _navigateToModule.value = true;
    }

    fun doneNavigating(){
        _navigateToModule.value = false;
    }
}