package com.anhht.edu.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.model.data.Product
import com.anhht.edu.model.data.Topic
import com.anhht.edu.model.data.TopicByLevel
import com.squareup.picasso.Picasso
import org.intellij.lang.annotations.RegExp

class RVProductAdapter(var context: Context, var products: List<Product>, var listener:IClickProductCard) : RecyclerView.Adapter<RVProductAdapter.ProductViewHolder>(){

    interface IClickProductCard{
        fun onClickItemProductCard(product: Product);
    }
    fun setFilteredList(list: List<Product>){
        products = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val list : List<Product> = products
        holder.name.text = list[position].name
        holder.price.text = String.format("%s dats",list[position].price.toString().replace("([.]*0+)(?!.*\\d)".toRegex(), ""))
        Picasso.get().load(list[position].image).into(holder.image)
        holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim))
        holder.card.setOnClickListener(View.OnClickListener { view ->
            Log.e("product", list[position].toString())
            listener.onClickItemProductCard(list[position])
        })
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.productCard)
        var name = itemView.findViewById<TextView>(R.id.productName)
        var price = itemView.findViewById<TextView>(R.id.productPrice)
        var image = itemView.findViewById<ImageView>(R.id.productImage)
    }
}
