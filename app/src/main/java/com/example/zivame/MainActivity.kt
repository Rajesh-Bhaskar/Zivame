package com.example.zivame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zivame.adaptor.ProductAdapter
import com.example.zivame.model.Product
import com.example.zivame.rest.APIService
import com.example.zivame.rest.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadProducts()
    }


    private fun loadProducts() {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(APIService::class.java)
        val requestCall = destinationService.getProductList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val productList = response.body()!!
                    Log.d("Response", "productlist size : ${productList.products.size}")
                    product_recycler.apply {
                        setHasFixedSize(true)
                        setPadding(10,10,10,10)
                        layoutManager = GridLayoutManager(this@MainActivity,1)
                        adapter = ProductAdapter(productList.products!!)
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()

            }
        })
    }

}