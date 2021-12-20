package com.example.zivame.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.zivame.R
import com.example.zivame.model.CartModelClass
import com.squareup.picasso.Picasso

class MyListAdapter(private val productList: List<CartModelClass>, private val cellCartClickListener: (CartModelClass) -> Unit) :RecyclerView.Adapter<MyListAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {


        var imageView = itemView.findViewById<ImageView>(R.id.ivFlag)
        var tvTitle = itemView.findViewById<TextView>(R.id.productTitle)
        var tvCases = itemView.findViewById<TextView>(R.id.price)
        var cart_item = itemView.findViewById<ImageView>(R.id.cart_item)
        fun bind(products: CartModelClass) {
            cart_item.isVisible = false
            val name ="Cases :${products.userName}"
            tvTitle.text = products.userName
            tvCases.text = products.price
            Picasso.get().load(products.userEmail).into(imageView);
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${productList.size} ")

        holder.itemView.setOnClickListener {
            cellCartClickListener(productList[position])
        }
        return holder.bind(productList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }
}