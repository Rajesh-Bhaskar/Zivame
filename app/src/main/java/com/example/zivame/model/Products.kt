package com.example.zivame.model

import com.google.gson.annotations.SerializedName

data class Products(
    val name: String,
    val price: String,
    val image_url: String,
    val rating: String,
)