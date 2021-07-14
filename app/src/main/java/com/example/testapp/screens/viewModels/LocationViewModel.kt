package com.example.testapp.screens.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.testapp.models.Image
import com.example.testapp.models.Location
import com.example.testapp.repositories.LocationRepository
import com.example.testapp.support.CoroutineViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationViewModel(
    private val locationRepository: LocationRepository,
) : CoroutineViewModel() {

    val locationsLiveData = locationRepository.locationLiveData.asLiveData()
    val imagesLiveData = locationRepository.imagesLiveData.asLiveData()

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()


    fun saveLocation(location: Location) = launch {
        val id = locationRepository.saveLocation(location)
        saveLocationToFireBase(Location(
            id = id,
            title = location.title
        ))
    }

    fun updateLocation(location: Location) = launch {
        locationRepository.updateLocation(location)
        updateLocationInFireBase(location)
    }

    fun deleteLocation(position: Int) = launch {
        val location = locationsLiveData.value?.get(position)
        location?.let { locationRepository.deleteLocation(it) }
        location?.let { deleteLocationFromFireBase(it) }
    }

    fun saveImage(location: Location, image: Image) = launch {
        val imageId = locationRepository.saveImage(image)
        saveImageToFireBase(location, image, imageId)
    }

    private suspend fun saveLocationToFireBase(location: Location) {
        withContext(Dispatchers.IO) {
            val document = firestore.collection(COLLECTION_PATH).document(location.id.toString())
            val set = document.set(location)
            set.addOnSuccessListener {
                Log.d(TAG_ADD_LOCATION, LOCATION_SUCCESS_MESSAGE)
            }
            set.addOnFailureListener { Log.d(TAG_ADD_LOCATION, LOCATION_ERROR_MESSAGE) }
        }
    }

    private suspend fun updateLocationInFireBase(location: Location) {
        withContext(Dispatchers.IO) {
            val collection = firestore.collection(COLLECTION_PATH).document(location.id.toString())
            collection.update(TITLE, location.title)
        }
    }

    private suspend fun saveImageToFireBase(location: Location, image: Image, imageId: Long) {
        withContext(Dispatchers.IO) {
            val collection = firestore.collection(COLLECTION_PATH)
                .document(location.id.toString())
                .collection(IMAGES_COLLECTION_PATH)
            image.id = imageId
            val task = collection.add(image)
            task.addOnSuccessListener {
                image.imageFirebaseId = it.id
                uploadImage(location, image)
                Log.d(TAG_ADD_IMAGE, IMAGE_SUCCESS)
            }
        }
    }

    private fun uploadImage(location: Location, image: Image) {
        launch {
            val uri = Uri.parse(image.localImageUri)
            val imageRef = storage.reference.child(IMAGES_STORAGE_PATH + uri.lastPathSegment)
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnSuccessListener {
                val downloadUrl = imageRef.downloadUrl
                downloadUrl.addOnSuccessListener {
                    image.remoteImageUri = it.toString()
                    updateImageDatabase(location, image)
                }
            }
            uploadTask.addOnFailureListener {
                Log.e(TAG_ADD_LOCATION, it.message.toString())
            }
        }
    }

    private fun updateImageDatabase(location: Location, image: Image) = launch {
        firestore.collection(COLLECTION_PATH)
            .document(location.id.toString())
            .collection(IMAGES_COLLECTION_PATH)
            .document(image.imageFirebaseId)
            .set(image)
    }

    private suspend fun deleteLocationFromFireBase(location: Location) {
        withContext(Dispatchers.IO) {
            val imagesCollection = firestore.collection(COLLECTION_PATH)
                .document(location.id.toString()).collection(IMAGES_COLLECTION_PATH).get()

            imagesCollection.addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    for (document in result.documents) {
                        document.reference.delete()
                            .addOnSuccessListener {
                                Log.d(TAG_DELETE_IMAGE, IMAGE_SUCCESS)
                            }
                            .addOnFailureListener {
                                Log.e(TAG_DELETE_IMAGE, it.message.toString())
                            }
                    }
                }
                deleteDocument(location)
            }
        }
    }

    private fun deleteDocument(location: Location) = launch {
        val document = firestore
            .collection(COLLECTION_PATH)
            .document(location.id.toString())
            .delete()
        document.addOnSuccessListener {
            Log.d(TAG_DELETE_LOCATION, LOCATION_DELETED)
        }
    }

    fun deleteImagesFromCloudStorage() = launch {
        val imagesListRef = storage.reference.child(IMAGES_STORAGE_PATH)
        imagesListRef.listAll()
            .addOnSuccessListener { items ->
                items.items.forEach { item ->
                    item.delete()
                        .addOnSuccessListener {
                            Log.d(DELETE_STORAGE_IMAGE, IMAGE_SUCCESS)
                        }
                        .addOnFailureListener {
                            Log.e(DELETE_STORAGE_IMAGE, it.message.toString())
                        }
                }
            }
            .addOnFailureListener {
                Log.e(DELETE_STORAGE_IMAGE, it.message.toString())
            }
    }


    companion object {
        private const val COLLECTION_PATH = "collections"
        private const val IMAGES_COLLECTION_PATH = "images"
        private const val IMAGES_STORAGE_PATH = "images/"

        private const val TITLE = "title"

        private const val TAG_ADD_LOCATION = "ADD LOCATION"
        private const val LOCATION_SUCCESS_MESSAGE = "LOCATION SUCCESSFULLY WRITTEN!"
        private const val LOCATION_ERROR_MESSAGE = "ERROR WRITING LOCATION"
        private const val TAG_DELETE_LOCATION = "DELETE LOCATION"
        private const val LOCATION_DELETED = "LOCATION_DELETED"

        private const val IMAGE_SUCCESS = "SUCCESS"
        private const val TAG_ADD_IMAGE = "ADD IMAGE"
        private const val TAG_DELETE_IMAGE = "DELETE_IMAGE"

        private const val DELETE_STORAGE_IMAGE = "DELETE FROM STORAGE"

    }
}
