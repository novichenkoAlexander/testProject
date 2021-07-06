package com.example.testapp.models

import java.io.Serializable

data class Location(
    val id: Long = 0L,
    val title: String? = "",
    var imageList: ArrayList<String> = ArrayList(),
) : Serializable


