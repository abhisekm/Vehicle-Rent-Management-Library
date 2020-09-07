package com.abhisekm.vehiclerentmanagementexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhisekm.vehiclerentmanagementexample.ui.main.MainFragment
import com.abhisekm.vehiclerentmanagementlibrary.Config
import com.abhisekm.vehiclerentmanagementlibrary.RentalConfig

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        RentalConfig.setUp(Config("custom api"))
    }
}