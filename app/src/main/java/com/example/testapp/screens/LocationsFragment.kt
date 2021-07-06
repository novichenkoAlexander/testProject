package com.example.testapp.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLocationsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testapp.adapters.LocationsListAdapter
import com.example.testapp.models.Location
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationsFragment : Fragment(R.layout.fragment_locations) {

    private val viewBinding: FragmentLocationsBinding by viewBinding()

    private val viewModel: LocationViewModel by viewModel()

    private lateinit var adapter: LocationsListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationsRecyclerView = viewBinding.rvLocations


        adapter = LocationsListAdapter(
            onAddClick = ::onAddImageClick,
            onTextChanged = ::onTitleTextChanged
        )
        locationsRecyclerView.adapter = adapter

        viewModel.locationsLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.fabAddLocation.setOnClickListener {
            viewModel.saveLocation(
                Location()
            )
        }

    }

    private fun onAddImageClick(location: Location) {

    }

    private fun onTitleTextChanged(text: String, location: Location) {
        viewModel.updateLocation(
            Location(id = location.id, title = text)
        )
    }

}