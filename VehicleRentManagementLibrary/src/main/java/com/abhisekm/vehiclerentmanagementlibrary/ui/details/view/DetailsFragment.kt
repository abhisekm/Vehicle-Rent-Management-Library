package com.abhisekm.vehiclerentmanagementlibrary.ui.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.DetailsFragmentBinding
import com.abhisekm.vehiclerentmanagementlibrary.ui.details.adapter.TripAdapter
import com.abhisekm.vehiclerentmanagementlibrary.ui.details.viewmodel.DetailsViewModel
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.adapter.BikesAdapter

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val arguments = DetailsFragmentArgs.fromBundle(requireArguments())
        ViewModelProvider(this, DetailsViewModel.Factory(arguments.id, activity.application))
            .get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DetailsFragmentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.details_fragment, container, false)

        val arguments = DetailsFragmentArgs.fromBundle(requireArguments())

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.listDays.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listDays.adapter = TripAdapter(TripAdapter.OnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToBookingFragment(arguments.id, it.id))
        })

        binding.listHourly.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listHourly.adapter = TripAdapter(TripAdapter.OnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToBookingFragment(arguments.id, it.id))
        })

        return binding.root
    }
}