package com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.viewmodel.BookingHistoryViewModel

class BookingHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = BookingHistoryFragment()
    }

    private lateinit var viewModel: BookingHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.booking_history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}