package com.anhht.edu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.model.data.Level
import com.squareup.picasso.Picasso

class RVLevelAdapter(var context: Context, private var list:List<Level>, private var listener: IClickLevelCard) : RecyclerView.Adapter<RVLevelAdapter.LevelViewHolder>(){

    interface IClickLevelCard{
        fun onClickItemLevelCard(level: Level);
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_level_item, parent, false)
        return LevelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        Picasso.get().load("https://imgur.com/"+list[position].levelName.split("thaidang")[1]).into(holder.img);
        holder.name.text = list[position].levelName.split("thaidang")[0]
        holder.progress.progress = list[position].process.toInt()
        holder.description.text = "Danh sách từ vựng " + list[position].levelName.split("thaidang")[0] + " bao gồm " + list[position].numTopics + " bài học và " + list[position].numWords + " từ vựng được phân loại theo chủ đề, độ khó và cách sử dụng theo CEFR."
        holder.card.setOnClickListener(View.OnClickListener {
            listener.onClickItemLevelCard(list[position])
        })
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    inner class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.cardLevel)
        var img = itemView.findViewById<ImageView>(R.id.levelImg)
        var name = itemView.findViewById<TextView>(R.id.levelName)
        var progress = itemView.findViewById<ProgressBar>(R.id.levelProgress)
        var description = itemView.findViewById<TextView>(R.id.levelDesc)
    }
}