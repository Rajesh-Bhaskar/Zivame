package com.example.zivame.rest

import com.example.zivame.model.Product
import com.example.zivame.model.Products
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    //https://my-json-server.typicode.com/nancymadan/assignment/db
    @GET("/nancymadan/assignment/db")
    fun getProductList() : Call<Product>
}