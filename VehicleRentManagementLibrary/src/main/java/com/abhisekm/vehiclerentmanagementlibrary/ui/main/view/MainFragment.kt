package com.abhisekm.vehiclerentmanagementlibrary.ui.main.view

import android.graphics.Path
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.abhisekm.vehiclerentmanagementlibrary.R
import com.abhisekm.vehiclerentmanagementlibrary.databinding.RentalMainFragmentBinding
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.adapter.BikesAdapter
import com.abhisekm.vehiclerentmanagementlibrary.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: RentalMainFragmentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.rental_main_fragment, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.listBikes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listBikes.adapter = BikesAdapter(BikesAdapter.OnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment(
                    it.id,
                    it.name
                )
            )
        })

        binding.viewModel?.eventNavigateToHistory?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToBookingHistoryFragment())
            }
        })

        return binding.root
    }


}