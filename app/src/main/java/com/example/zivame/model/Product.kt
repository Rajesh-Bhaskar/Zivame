package com.example.zivame.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("products")
    val products: List<Products>
)
