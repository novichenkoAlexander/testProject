package com.example.testapp.screens

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
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
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testapp.dialogs.ImageDialog


class LocationsFragment : Fragment(R.layout.fragment_locations) {

    private val viewBinding: FragmentLocationsBinding by viewBinding()

    private val locationViewModel: LocationViewModel by viewModel()
    private val imageViewModel: ImageViewModel by viewModel()

    private lateinit var adapter: LocationsListAdapter

    private lateinit var location: Location


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationsListAdapter(
            onAddClick = ::onAddImageClick,
            onTextChanged = ::onTitleTextChanged,
            onAdapterSet = ::onAdapterSet,
            onImageClick = ::onImageClick,
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

    @Suppress("DEPRECATION")
    private fun onImageClick(imageUri: String) {
        fragmentManager?.let {
            ImageDialog(Uri.parse(imageUri)).show(it, ImageDialog.DIALOG_TAG)
        }
    }

    //get photo uri
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.data

            imageViewModel.saveImage(
                Image(
                    locationId = location.id,
                    imageUri = data.toString()
                )
            )
        }
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


    @Suppress("DEPRECATION")
    private fun onAddImageClick(location: Location) {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2000)
        } else {
            startGallery()
        }
        this.location = location
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            resultLauncher.launch(intent)
        }
    }

    private fun onTitleTextChanged(text: String, location: Location) {
        locationViewModel.updateLocation(
            Location(id = location.id, title = text)
        )
    }

}