package com.example.testapp.models

import androidx.room.Embedded
import androidx.room.Relation

class LocationContent(

    @Embedded
    val location: Location,

    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )

    val images: List<Image>
)