package com.abhisekm.vehiclerentmanagementlibrary.ui.booking.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.BookingFragmentBinding
import com.abhisekm.vehiclerentmanagementlibrary.ui.booking.viewmodel.BookingViewModel
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

class BookingFragment() : Fragment() {

    private val PAYMENT_RC: Int = 1001
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
                showDatePicker("Select Start Time", this, listener = object : (Long) -> Unit {
                    override fun invoke(p1: Long) {
                        viewModel.updateStartTime(p1)
                    }
                })
            }
        })

        binding.viewModel?.eventShowEndPicker?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.apply {
                showDatePicker("Select End Time", this.second, listener = object : (Long) -> Unit {
                    override fun invoke(p1: Long) {
                        viewModel.updateEndTime(p1)
                    }
                }, this.first)
            }
        })

        binding.viewModel?.eventStartPayment?.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                it?.getContentIfNotHandled()?.let {
                    val intent = Intent()
                    intent.apply {
                        this.action = Intent.ACTION_VIEW
                        this.data = Uri.parse(it)
                        val intentChooser = Intent.createChooser(this, "Pay with ...")
                        if (intentChooser.resolveActivity(requireContext().packageManager) != null) {
                            startActivityForResult(intentChooser, PAYMENT_RC)
                        }
                    }
                }
            })

        binding.viewModel?.eventErrorMessage?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })

        binding.viewModel?.eventPaymentSuccess?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let {
                findNavController().navigate(BookingFragmentDirections.actionBookingFragmentToBookingHistoryFragment())
            }
        })

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Booking", "onActivityResult: $requestCode , $resultCode , $data")
        if (requestCode == PAYMENT_RC) {
            viewModel.paymentSuccess()
        }
    }

    private fun showDatePicker(
        title: String,
        currentTime: Long,
        listener: (Long) -> Unit,
        minDate: Long = viewModel.getCurrentTime()
    ) {
        CardDatePickerDialog.builder(requireContext())
            .setTitle(title)
            .setOnCancel("Cancel")
            .setOnChoose("Set", listener)
            .setDefaultTime(currentTime)
//            .setMinTime(minDate)
            .showFocusDateInfo(false)
            .showBackNow(false)
            .showDateLabel(false)
            .build().show()
    }

}