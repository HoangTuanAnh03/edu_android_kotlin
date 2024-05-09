package com.anhht.edu.views.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.model.data.Topic
import com.anhht.edu.model.data.TopicByLevel

class RVTopicAdapter(var context: Context, var topicByLevel: TopicByLevel, var listener:IClickTopicCard) : RecyclerView.Adapter<RVTopicAdapter.TopicViewHolder>(){

    interface IClickTopicCard{
        fun onClickItemTopicCard(topic: Topic);
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_topic_item, parent, false)
        return TopicViewHolder(view)
    }
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val list : List<Topic> = topicByLevel.listTopics
        //Picasso.with(context).load("https://imgur.com/"+list[position].levelName.split("thaidang")[1]).into(holder.img);
        holder.name.text = list[position].topic
        holder.progress.progress = list[position].process.toInt()
        holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim))
        holder.card.setOnClickListener(View.OnClickListener { view ->
            Log.e("topic", list[position].toString())
            listener.onClickItemTopicCard(list[position])
        })
    }

    override fun getItemCount(): Int {
        return topicByLevel.listTopics.count()
    }

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.cardTopic)
        var name = itemView.findViewById<TextView>(R.id.topicName)
        var progress = itemView.findViewById<ProgressBar>(R.id.topicProgress)
    }
}