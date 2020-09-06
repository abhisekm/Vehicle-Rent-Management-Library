package com.abhisekm.vehiclerentmanagementlibrary.ui.booking.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.BookingFragmentBinding
import com.abhisekm.vehiclerentmanagementlibrary.ui.booking.viewmodel.BookingViewModel
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import java.util.*

class BookingFragment : Fragment() {


    private val viewModel: BookingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val arguments = BookingFragmentArgs.fromBundle(requireArguments())
        ViewModelProvider(
            this,
            BookingViewModel.Factory(arguments.bikeId, arguments.tripId, activity.application)
        )
            .get(BookingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: BookingFragmentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.booking_fragment, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.viewModel?.eventShowStartPicker?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.apply {
                showDatePicker(this, listener = object : (Long) -> Unit {
                    override fun invoke(p1: Long) {
                        viewModel.updateStartTime(p1)
                    }
                })
            }
        })

        binding.viewModel?.eventShowEndPicker?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.apply {
                showDatePicker(this.second, listener = object : (Long) -> Unit {
                    override fun invoke(p1: Long) {
                        viewModel.updateEndTime(p1)
                    }
                }, this.first)
            }
        })

        return binding.root
    }

    private fun showDatePicker(currentTime: Long, listener: (Long) -> Unit , minDate : Long = Calendar.getInstance().timeInMillis) {
        CardDatePickerDialog.builder(requireContext())
            .setTitle("SET MAX DATE")
            .setOnCancel("Cancel")
            .setOnChoose("Select Dates", listener)
            .setDefaultTime(currentTime)
            .setMinTime(minDate)
            .showFocusDateInfo(false)
            .showBackNow(false)
            .showDateLabel(false)
            .build().show()
    }

}