package com.example.testapp.database

import androidx.room.*
import com.example.testapp.models.Image
import com.example.testapp.models.LocationContent
import kotlinx.coroutines.flow.Flow


@Dao
abstract class ImagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveImage(image: Image)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveImagesList(images: ArrayList<Image>)

    @Delete
    abstract fun deleteImage(image: Image)

    @Query("SELECT * FROM images")
    abstract fun getImagesFlow(): Flow<List<Image>>

    @Transaction
    @Query("SELECT * FROM images WHERE locationId =:locationId")
    abstract fun getLocationImagesListFlow(locationId: Long): Flow<List<Image>?>
}