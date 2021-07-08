package com.example.testapp.screens.viewModels

import androidx.lifecycle.asLiveData
import com.example.testapp.models.Image
import com.example.testapp.repositories.LocationRepository
import com.example.testapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ImageViewModel(
    private val locationRepository: LocationRepository,
) : CoroutineViewModel() {


    val imagesLiveData = locationRepository.imagesLiveData.asLiveData()

    fun saveImage(image: Image) = launch {
        locationRepository.saveImage(image)
    }

    fun deleteImage(image: Image) = launch {
        locationRepository.deleteImage(image)
    }


}