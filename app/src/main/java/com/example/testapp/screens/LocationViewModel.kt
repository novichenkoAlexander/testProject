package com.example.testapp.screens

import androidx.lifecycle.asLiveData
import com.example.testapp.database.LocationsDao
import com.example.testapp.models.Location
import com.example.testapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationsDao: LocationsDao,
) : CoroutineViewModel() {

    val locationsLiveData = locationsDao.getLocationsFLow().asLiveData()


    fun saveLocation(location: Location) {
        launch {
            locationsDao.saveLocation(location)
        }
    }

    fun updateLocation(location: Location) {
        launch {
            locationsDao.updateLocation(location)
        }
    }


}