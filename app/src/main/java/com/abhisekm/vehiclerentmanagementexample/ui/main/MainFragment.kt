package com.abhisekm.vehiclerentmanagementexample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abhisekm.vehiclerentmanagementexample.R
import com.abhisekm.vehiclerentmanagementexample.databinding.MainFragmentBinding
import com.abhisekm.vehiclerentmanagementlibrary.RentalActivity

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(layoutInflater,R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToModule.observe(viewLifecycleOwner, Observer {
            if(it){
                startActivity(Intent(requireContext(), RentalActivity::class.java))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

}