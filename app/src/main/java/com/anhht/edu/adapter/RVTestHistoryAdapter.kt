package com.anhht.edu.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.model.data.TestHistory
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RVTestHistoryAdapter (var context: Context, var tests: List<TestHistory>) : RecyclerView.Adapter<RVTestHistoryAdapter.TestHistoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHistoryViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_test, parent, false)
        return TestHistoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: TestHistoryViewHolder, position: Int) {
        val list : List<TestHistory> = tests
        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy")
        val zonedDateTime = ZonedDateTime.parse(list[position].tdate.toString(), formatter)
        val localDate = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate()
        val formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val percentage = ((list[position].numcorrectques.toFloat() / list[position].numques.toFloat() ) *100 ).toInt()
        holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycleview_anim))
        if(percentage >= 60){
            holder.card.background.setTint(Color.parseColor("#A2EFA5"))
        }else{
            holder.card.background.setTint(Color.parseColor("#efa2a8"))
        }

        holder.testName.text = String.format("Ngày Kiểm Tra: %s", formattedDate)
        holder.testNumAns.text = String.format("Số câu: %s", list[position].numques)
        holder.testScore.progress = percentage
        holder.testScoreTxt.text = "$percentage %"
        holder.testNumTrueAns.text = String.format("Số câu đúng: %s", list[position].numcorrectques)
    }

    override fun getItemCount(): Int {
        return tests.count()
    }

    inner class TestHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.resultTestCard)
        var testName = itemView.findViewById<TextView>(R.id.testName)
        var testNumTrueAns = itemView.findViewById<TextView>(R.id.testNumTrueAns)
        var testNumAns = itemView.findViewById<TextView>(R.id.testNumAns)
        var testScoreTxt = itemView.findViewById<TextView>(R.id.testScoreTxt)
        var testScore = itemView.findViewById<CircularProgressIndicator>(R.id.testScore)
    }
}