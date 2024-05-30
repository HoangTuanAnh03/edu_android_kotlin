package com.anhht.edu.views.test

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityTestResultsBinding
import com.anhht.edu.repository.TestHistoryAPIService
import com.anhht.edu.viewmodels.TestHistoryViewModel
import com.anhht.edu.adapter.RVTestResultAdapter

class TestResultsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestResultsBinding
    lateinit var testHistoryVM: TestHistoryViewModel

    private lateinit var rv : RecyclerView
    private lateinit var testResultAdapter : RVTestResultAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultsBinding.inflate(layoutInflater)
        val bundle = intent.extras

        val userAnswerList = bundle!!.get("userAnswerList") as ArrayList<String>
        val dbAnswerList = bundle.get("dbAnswerList") as ArrayList<String>
        val questionList = bundle.get("questionList") as ArrayList<String>
        val matchCount = bundle.get("matchCount") as Int

        val totalQuestions = questionList.size
        val percentage = ((matchCount.toFloat() / totalQuestions.toFloat() ) *100 ).toInt()
        binding.scoreProgressText.text = "$percentage %"
        binding.scoreProgressIndicator.progress = percentage

        if(percentage>60){
            binding.scoreTitle.text = "Chúc mừng bạn\nđã hoàn thành xuất sắc"
//            binding.resultImg.setImageResource(R.drawable.congrats)
            binding.scoreTitle.setTextColor(Color.BLUE)
        }else{
            binding.scoreTitle.text = "Hãy chăm chỉ hơn nhé"
//            binding.resultImg.setImageResource(R.drawable.failed)
            binding.scoreTitle.setTextColor(Color.RED)
        }
        binding.scoreSubtitle.text = "Bạn đã trả lời đúng $matchCount/$totalQuestions"
        testHistoryVM = TestHistoryViewModel(TestHistoryAPIService())
        testHistoryVM.addTestHistory(this@TestResultsActivity, totalQuestions, matchCount).observe(this){ data->
            if(data != null){
                Toast.makeText(this, "Lưu kết quả kiểm tra thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Có lỗi trong quá trình lưu kết quả", Toast.LENGTH_SHORT).show()
            }
        }
        val decoration : RecyclerView.ItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        testResultAdapter = RVTestResultAdapter(this, userAnswerList, dbAnswerList, questionList)
        rv = binding.resultRV

        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(decoration)
        rv.adapter = testResultAdapter
        binding.resultTestBack.setOnClickListener{
            finish()
        }
        binding.finishBtn.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }
}