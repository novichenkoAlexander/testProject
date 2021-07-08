package com.example.testapp.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLocationsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testapp.adapters.ImagesListAdapter
import com.example.testapp.adapters.LocationsListAdapter
import com.example.testapp.models.Image
import com.example.testapp.models.Location
import com.example.testapp.screens.viewModels.ImageViewModel
import com.example.testapp.screens.viewModels.LocationViewModel
import com.example.testapp.support.SwipeHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationsFragment : Fragment(R.layout.fragment_locations) {

    private val viewBinding: FragmentLocationsBinding by viewBinding()

    private val locationViewModel: LocationViewModel by viewModel()
    private val imageViewModel: ImageViewModel by viewModel()

    private lateinit var adapter: LocationsListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = LocationsListAdapter(
            onAddClick = ::onAddImageClick,
            onTextChanged = ::onTitleTextChanged,
            onAdapterSet = ::onAdapterSet
        )
        viewBinding.rvLocations.adapter = adapter

        locationViewModel.locationsLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewBinding.fabAddLocation.setOnClickListener {
            locationViewModel.saveLocation(
                Location()
            )
        }

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(viewBinding.rvLocations) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                return listOf(deleteButton(position))
            }
        })

        itemTouchHelper.attachToRecyclerView(viewBinding.rvLocations)
    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            20.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    locationViewModel.deleteLocation(position)
                }
            })
    }

    private fun onAdapterSet(location: Location, adapter: ImagesListAdapter) {
        imageViewModel.imagesLiveData.observe(this.viewLifecycleOwner, { images ->
            val imageList = ArrayList<Image>()
            for (image in images) {
                if (image.locationId == location.id) {
                    imageList.add(image)
                }
            }
            adapter.submitList(imageList)
        })
    }


    private fun onAddImageClick(location: Location) {
        imageViewModel.saveImage(
            Image(
                locationId = location.id,
                image = "Image"
            )
        )
    }

    private fun onTitleTextChanged(text: String, location: Location) {
        locationViewModel.updateLocation(
            Location(id = location.id, title = text)
        )
    }

}