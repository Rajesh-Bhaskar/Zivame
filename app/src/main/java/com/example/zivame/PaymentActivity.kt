package com.example.zivame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zivame.adaptor.MyListAdapter
import com.example.zivame.database.DatabaseHandler
import com.example.zivame.model.CartModelClass
import com.example.zivame.model.CellCartClickListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.*

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        Timer().schedule(object : TimerTask() {
            override fun run() {
               Intent(this@PaymentActivity,SuccessActivity::class.java).apply {
                   startActivity(this)
               }
            }
        }, 30000)
    }



}


