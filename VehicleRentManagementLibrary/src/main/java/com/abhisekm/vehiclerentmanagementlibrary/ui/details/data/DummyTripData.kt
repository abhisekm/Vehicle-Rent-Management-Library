package com.abhisekm.vehiclerentmanagementlibrary.ui.details.data

import com.abhisekm.vehiclerentmanagementlibrary.domain.Trip

val DummyTripData = listOf<Trip>(
    Trip(
        id = 1,
        type = "hour",
        rate = 10,
        discount = 5,
        bestSeller = false,
        duration = 6,
    ),
    Trip(
        id = 8,
        type = "hour",
        rate = 10,
        discount = 10,
        bestSeller = false,
        duration = 12,
    ),
    Trip(
        id = 2,
        type = "hour",
        rate = 10,
        discount = 0,
        bestSeller = false,
        duration = -1,
    ),
    Trip(
        id = 3,
        type = "day",
        rate = 10,
        discount = 10,
        bestSeller = false,
        duration = 1,
    ),
    Trip(
        id = 4,
        type = "day",
        rate = 10,
        discount = 15,
        bestSeller = true,
        duration = 7,
    ),
    Trip(
        id = 5,
        type = "day",
        rate = 10,
        discount = 20,
        bestSeller = false,
        duration = 15,
    ),
    Trip(
        id = 6,
        type = "day",
        rate = 10,
        discount = 25,
        bestSeller = false,
        duration = 30,
    ),
    Trip(
        id = 7,
        type = "day",
        rate = 10,
        discount = 0,
        bestSeller = false,
        duration = -1,
    ),
)