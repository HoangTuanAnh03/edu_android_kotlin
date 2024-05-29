package com.anhht.edu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.model.data.OrderHistory
import com.anhht.edu.model.data.Product
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class RVOrderHistoryAdapter (var context: Context, var history: List<OrderHistory>) : RecyclerView.Adapter<RVOrderHistoryAdapter.OrderHistoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return OrderHistoryViewHolder(view)
    }
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val list : List<OrderHistory> = history
        holder.name.text = list[position].pname
        holder.quantity.text = String.format("Số lượng: %s",list[position].quantity.toString())
        holder.price.text = String.format("Giá: %s dats",(list[position].price * list[position].quantity).toString().replace("([.]*0+)(?!.*\\d)".toRegex(), ""))
        holder.address.text = String.format("Nơi nhận: %s", list[position].address)

        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy")
        val zonedDateTime = ZonedDateTime.parse(list[position].date.toString(), formatter)
        val localDate = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate()
        val formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
        val daysDiff = now.until(zonedDateTime, ChronoUnit.DAYS)
        holder.date.text = String.format("Ngày đổi: %s", formattedDate)
        Picasso.get().load(list[position].pimage).into(holder.image)
        holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim))
        if(daysDiff < -15){ // đã hoàn thành
            holder.card.background.setTint(Color.parseColor("#A2EFA5"))
        }else if(daysDiff < -6){ //đang chuyển
            holder.card.background.setTint(Color.parseColor("#F6EDA1"))
        }else{
            holder.card.background.setTint(Color.parseColor("#ffffff"))
        }
    }

    override fun getItemCount(): Int {
        return history.count()
    }

    inner class OrderHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.historyCard)
        var name = itemView.findViewById<TextView>(R.id.historyName)
        var quantity = itemView.findViewById<TextView>(R.id.quantity)
        var price = itemView.findViewById<TextView>(R.id.price)
        var address = itemView.findViewById<TextView>(R.id.address)
        var date = itemView.findViewById<TextView>(R.id.date)
        var image = itemView.findViewById<ImageView>(R.id.historyImage)
    }
}
