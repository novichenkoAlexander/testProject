package com.example.testapp.database

import androidx.room.*
import com.example.testapp.models.Location
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveLocation(location: Location): Long

    @Delete
    abstract fun deleteLocation(location: Location)

    @Update
    abstract fun updateLocation(location: Location)

    @Query("SELECT * FROM locations")
    abstract fun getLocationsFLow(): Flow<List<Location>>

}