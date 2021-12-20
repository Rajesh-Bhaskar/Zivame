package com.example.zivame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zivame.adaptor.MyListAdapter
import com.example.zivame.adaptor.ProductAdapter
import com.example.zivame.database.DatabaseHandler
import com.example.zivame.fragments.CartFragment
import com.example.zivame.model.CartModelClass
import com.example.zivame.model.CellCartClickListener
import com.example.zivame.model.CellClickListener
import com.example.zivame.model.Products
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.listView
import java.util.*

class CartActivity : AppCompatActivity(), CellCartClickListener {

    var primaryProgressStatus = 0
    var secondaryHandler: Handler? = Handler()
    var secondaryProgressStatus = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        viewRecord()

    }

    fun viewRecord(){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        val cart: List<CartModelClass> = databaseHandler.viewItemsincart()
        val empArrayId = Array<String>(cart.size){"0"}
        val empArrayName = Array<String>(cart.size){"null"}
        val empArrayEmail = Array<String>(cart.size){"null"}
        var index = 0
        for(e in cart){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            index++
        }
        //creating custom ArrayAdapter
        listView.apply {
            setHasFixedSize(true)
            setPadding(10,10,10,10)
            layoutManager = GridLayoutManager(this@CartActivity,1)
            adapter = MyListAdapter(cart!!,{ product -> cellCartClickListener(product) })
        }

    }

    fun startSecondaryProgress() {
        Thread(Runnable {
            while (secondaryProgressStatus < 100) {
                secondaryProgressStatus += 1

                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBarSecondary.setSecondaryProgress(secondaryProgressStatus)
                    textViewSecondary.setText("Current task progress\n$secondaryProgressStatus% of 100")

                    if (secondaryProgressStatus == 100) {
                        textViewSecondary.setText("Single task complete.")
                    }
                }
            }
        }).start()
    }
    fun viewcheckout(view: View){
        Intent(this, PaymentActivity::class.java).apply {
            startActivity(this)
        }
    }
    override fun cellCartClickListener(products: CartModelClass) {
        Toast.makeText(this,"Cell clicked  :"+products.toString(), Toast.LENGTH_SHORT).show()

    }


}


