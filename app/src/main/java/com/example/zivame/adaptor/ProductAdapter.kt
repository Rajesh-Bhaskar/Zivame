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
import com.example.zivame.model.Products
import com.squareup.picasso.Picasso

class ProductAdapter(private val productList: List<Products>,private val cellClickListener: (Products) -> Unit) :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${productList.size} ")

        holder.itemView.setOnClickListener {
            cellClickListener(productList[position])
        }
        return holder.bind(productList[position])

    }
    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {


        var imageView = itemView.findViewById<ImageView>(R.id.ivFlag)
        var tvTitle = itemView.findViewById<TextView>(R.id.productTitle)
        var tvCases = itemView.findViewById<TextView>(R.id.price)

        fun bind(products: Products) {

            val name ="Cases :${products.name}"
            tvTitle.text = products.name
            tvCases.text = products.price
            Picasso.get().load(products.image_url).into(imageView);
        }

    }
}