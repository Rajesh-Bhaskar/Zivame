package com.example.zivame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zivame.adaptor.ProductAdapter
import com.example.zivame.database.DatabaseHandler
import com.example.zivame.fragments.CartFragment
import com.example.zivame.model.CellClickListener
import com.example.zivame.model.CartModelClass
import com.example.zivame.model.Product
import com.example.zivame.model.Products
import com.example.zivame.rest.APIService
import com.example.zivame.rest.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cartempty.isVisible = false
        cart_item.isVisible = true
        items_count.isVisible = true
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
      //  databaseHandler.delete()
        loadcartitem()
        loadProducts()



    }
    fun viewRecord(view: View){
            //details_fragment.isVisible = true
        // initial transaction should be wrapped like this
        Intent(this, CartActivity::class.java).apply {
            startActivity(this)
        }
    }
    @SuppressLint("ResourceType")
    private fun loadcartitem() {
        var id = 0
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        val cart: List<CartModelClass> = databaseHandler.viewItemsincart()
        if (cart.size == 0){
            id= 0
            cartempty.isVisible = false
            cart_item.isVisible = true
            items_count.isVisible = true
            items_count.setText("0")
        }else{
            id= cart.size
            cartempty.isVisible = false
            cart_item.isVisible = true
            items_count.isVisible = true
            items_count.setText((cart.size).toString())
        }
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
                        adapter = ProductAdapter(productList.products!!,{ product -> onCellClickListener(product) })
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

    override fun onCellClickListener(product: Products) {
        //Toast.makeText(this,"Cell clicked  :"+product.toString(), Toast.LENGTH_SHORT).show()
        saveRecord(product)
    }

    fun saveRecord(product: Products){
        var id = 0
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        val cart: List<CartModelClass> = databaseHandler.viewItemsincart()
        if (cart.size == 0){
            id= 1
            cartempty.isVisible = false
            cart_item.isVisible = true
            items_count.isVisible = true
        }else{
            id= cart.size+1
            cartempty.isVisible = false
            cart_item.isVisible = true
            items_count.isVisible = true
        }
        val name = product.name
        val email = product.image_url
        val price = product.price
      //  val image_url = product.image_url
        if( name.trim()!="" && email.trim()!=""){
            val status = databaseHandler.addItem(CartModelClass(Integer.parseInt(id.toString()),name, email,price))
            if(status > -1){
               // Toast.makeText(applicationContext,"record save",Toast.LENGTH_LONG).show()
                if (cart.size == 0){
                    id= 1
                    cartempty.isVisible = false
                    cart_item.isVisible = true
                    items_count.isVisible = true
                    items_count.setText("1")
                }else{
                    id= cart.size+1
                    cartempty.isVisible = false
                    cart_item.isVisible = true
                    items_count.isVisible = true
                    items_count.setText((cart.size+1).toString())
                }

                Toast.makeText(this@MainActivity, " ${name} was added to cart", Toast.LENGTH_SHORT).show()

            }
        }else{
            Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
        }

    }

}


