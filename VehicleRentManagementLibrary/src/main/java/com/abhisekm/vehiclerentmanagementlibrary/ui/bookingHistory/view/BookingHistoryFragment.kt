package com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.BookingHistoryFragmentBinding
import com.abhisekm.vehiclerentmanagementlibrary.domain.Booking
import com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.adapter.BookingHistoryAdapter
import com.abhisekm.vehiclerentmanagementlibrary.ui.bookingHistory.viewmodel.BookingHistoryViewModel

class BookingHistoryFragment : Fragment() {

    private val viewModel: BookingHistoryViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProvider(
            this,
            BookingHistoryViewModel.Factory(activity.application)
        )
            .get(BookingHistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: BookingHistoryFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.booking_history_fragment, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.listBooking.adapter =
            BookingHistoryAdapter(onClickListener = BookingHistoryAdapter.OnClickListener { booking, unlock ->
                if (unlock) {
                    Toast.makeText(requireContext(), "Unlocked", Toast.LENGTH_SHORT).show()
                    viewModel.unlock(booking)
                } else {
                    Toast.makeText(requireContext(), "Locked", Toast.LENGTH_SHORT).show()
                    viewModel.lock(booking)
                }
            })

        return binding.root
    }
}