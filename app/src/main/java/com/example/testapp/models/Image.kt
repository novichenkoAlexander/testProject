package com.example.testapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "images")
data class Image(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val name: String = "",
) : Parcelable