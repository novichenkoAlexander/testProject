package com.example.testapp.repositories

import com.example.testapp.database.ImagesDao
import com.example.testapp.database.LocationsDao
import com.example.testapp.models.Image
import com.example.testapp.models.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(
    private val locationsDao: LocationsDao,
    private val imagesDao: ImagesDao,
) {

    val locationLiveData = locationsDao.getLocationsFLow()

    val imagesLiveData = imagesDao.getImagesFlow()


    suspend fun saveLocation(location: Location) {
        withContext(Dispatchers.IO) {
            locationsDao.saveLocation(location)
        }
    }

    suspend fun updateLocation(location: Location) {
        withContext(Dispatchers.IO) {
            locationsDao.updateLocation(location)
        }
    }

    suspend fun deleteLocation(location: Location) {
        withContext(Dispatchers.IO) {
            locationsDao.deleteLocation(location)
        }
    }

    suspend fun saveImage(image: Image) {
        withContext(Dispatchers.IO) {
            imagesDao.saveImage(image)
        }
    }

    suspend fun deleteImage(image: Image) {
        withContext(Dispatchers.IO) {
            imagesDao.deleteImage(image)
        }
    }

}