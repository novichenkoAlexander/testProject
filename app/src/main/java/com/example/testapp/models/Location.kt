package com.example.testapp.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "locations", indices = [Index(value = ["id"], unique = true)])
data class Location(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    val title: String = "",
) : Parcelable
