package com.anhht.edu.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R


class RVTestResultAdapter (var context: Context, var userAns: List<String>, var trueAns: List<String>, var question: List<String>) : RecyclerView.Adapter<RVTestResultAdapter.TestResultViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_result_test, parent, false)
        return TestResultViewHolder(view)
    }
    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        holder.resultIndex.text = String.format("%s. ", position + 1)
        holder.resultTrueAns.text = String.format("Đáp án đúng: %s", trueAns[position])
        holder.resultUserAns.text = String.format("Đáp án của bạn: %s", userAns[position])
        if(trueAns[position] != userAns[position]){
            holder.resultUserAns.paintFlags = (holder.resultUserAns.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
        }else{
            holder.resultUserAns.paintFlags = (holder.resultUserAns.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG)
        }
        holder.questionDetail.text = String.format("%s. ", question[position])
    }

    override fun getItemCount(): Int {
        return userAns.count()
    }

    inner class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var resultTrueAns = itemView.findViewById<TextView>(R.id.resultTrueAns)
        var resultUserAns = itemView.findViewById<TextView>(R.id.resultUserAns)
        var resultIndex = itemView.findViewById<TextView>(R.id.resultIndex)
        var questionDetail = itemView.findViewById<TextView>(R.id.questionDetail)
    }
}