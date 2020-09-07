package com.abhisekm.vehiclerentmanagementlibrary

/**
 * Demo class to test the library module
 */

data class Config(
    val api : String = "Default Value",

)

private lateinit var INSTANCE: RentalConfig

class RentalConfig(val config: Config) {
    companion object{
        fun setUp(config: Config){
            INSTANCE = RentalConfig(config)
        }

        fun getInstance(): RentalConfig {
            synchronized(RentalConfig::class) {
                if (!::INSTANCE.isInitialized) {
                   INSTANCE = RentalConfig(Config())
                }

                return INSTANCE
            }
        }
    }
}