package com.example.zivame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zivame.R
import com.example.zivame.adaptor.MyListAdapter
import com.example.zivame.adaptor.ProductAdapter
import com.example.zivame.database.DatabaseHandler
import com.example.zivame.model.CellCartClickListener
import com.example.zivame.model.CartModelClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment  : Fragment() ,CellCartClickListener{
    companion object{
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)


    }
    fun viewRecord(view: View){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(activity)

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


    }

    override fun cellCartClickListener(products: CartModelClass) {
        TODO("Not yet implemented")
    }


}