package com.example.testapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "images",
    foreignKeys = [ForeignKey(
        entity = Location::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("locationId"),
        onDelete = ForeignKey.CASCADE,
    )])
data class Image(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "locationId")
    val locationId: Long,

    val imageUri: String = "",

    val checked: Boolean = false,
)