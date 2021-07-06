package com.example.testapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.BuildConfig
import com.example.testapp.models.Location


@Database(
    entities = [
        Location::class
    ],
    version = 1,
    exportSchema = false
)


abstract class MyTestAppDatabase : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao
}

object DataBaseConstructor {
    fun create(context: Context): MyTestAppDatabase =
        Room.databaseBuilder(
            context,
            MyTestAppDatabase::class.java,
            "${BuildConfig.APPLICATION_ID}.db"
        ).build()
}