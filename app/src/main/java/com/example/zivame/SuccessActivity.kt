package com.example.zivame

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

    }

    fun continueShopping(view: View){
        Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY ).apply {
            startActivity(this)
        }
    }
}
