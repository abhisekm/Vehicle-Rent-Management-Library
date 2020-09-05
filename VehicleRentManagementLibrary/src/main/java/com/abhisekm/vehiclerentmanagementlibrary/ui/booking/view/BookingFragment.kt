package com.abhisekm.vehiclerentmanagementlibrary.ui.booking.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.ui.booking.viewmodel.BookingViewModel

class BookingFragment : Fragment() {

    companion object {
        fun newInstance() = BookingFragment()
    }

    private lateinit var viewModel: BookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}