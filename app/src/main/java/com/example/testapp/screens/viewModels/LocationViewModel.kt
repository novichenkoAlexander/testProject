package com.example.testapp.screens.viewModels

import androidx.lifecycle.asLiveData
import com.example.testapp.models.Location
import com.example.testapp.repositories.LocationRepository
import com.example.testapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationRepository: LocationRepository,
) : CoroutineViewModel() {

    val locationsLiveData = locationRepository.locationLiveData.asLiveData()

    fun saveLocation(location: Location) = launch {
        locationRepository.saveLocation(location)
    }

    fun updateLocation(location: Location) = launch {
        locationRepository.updateLocation(location)
    }

    fun deleteLocation(position: Int) = launch {
        val location = locationsLiveData.value?.get(position)
        locationRepository.deleteLocation(location!!)
    }


}