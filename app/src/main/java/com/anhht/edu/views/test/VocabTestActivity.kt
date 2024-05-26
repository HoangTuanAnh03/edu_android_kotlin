package com.anhht.edu.views.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityVocabTestBinding
import com.anhht.edu.model.data.Question
import com.anhht.edu.repository.WordAPIService
import com.anhht.edu.viewmodels.WordViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VocabTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityVocabTestBinding
    private var data: List<Question>?=null
    private var isClickBtn: Boolean = false
    private var btn_click: Button?=null
    private var valueChoose: String = ""
    private var userAnswerList : ArrayList<String> = ArrayList()
    private var dbAnswerList : ArrayList<String> = ArrayList()
    private var questionList : ArrayList<String> = ArrayList()
    private var time: String = "10"
    var i = 0
    private lateinit var wordViewModel: WordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVocabTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wordViewModel = WordViewModel(WordAPIService())
        wordViewModel.getTest().observe(this@VocabTestActivity){question->
            if(question.data == null){
                Toast.makeText(this, question.message, Toast.LENGTH_LONG).show()
                finish()
            }else{
                data = question.data
                Log.e("data", data!!.size.toString())
                binding.preBtn.visibility = View.GONE
                time = data!!.size.toString()
                GlobalScope.launch (Dispatchers.Main){
                    getQuestion(0)
                }
            }
        }
        startTimer()

        binding.nextBtn.setOnClickListener{
            if(isClickBtn == true){
                isClickBtn = false
                resetChoose()
                i++
                data.let{
                    if(i < it?.size!!){
                        getQuestion(i)
                        if(i == it.size.minus(1)){
                            binding.nextBtn.text = "Kết thúc"
                        }else{
                            binding.preBtn.visibility = View.VISIBLE
                        }
                    }else{
                        var matchCount = 0
                        for (i in userAnswerList.indices) {
                            if (userAnswerList[i] == dbAnswerList[i]) {
                                matchCount++
                            }
                        }
                        val intent = Intent(this, TestResultsActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("userAnswerList", userAnswerList)
                        bundle.putSerializable("dbAnswerList", dbAnswerList)
                        bundle.putSerializable("matchCount", matchCount)
                        bundle.putSerializable("questionList", questionList)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        binding.preBtn.setOnClickListener{
            resetChoose()
            Log.e("i", i.toString())
            data.let{
                if(i > 0){
                    i -= 1
                    getQuestion(i)
                    binding.nextBtn.text = "Tiếp Theo"
                    if(i == 0){
                        binding.preBtn.visibility = View.GONE
                    }else{
                        binding.preBtn.visibility = View.VISIBLE
                    }
                }else{
//                        finish()
                }
            }
        }
        binding.testBack.setOnClickListener {
            finish()
        }
    }
    public fun ClickChoose(view: View){
        btn_click = view as Button
        if(isClickBtn){
            resetChoose()
        }
        chooseBtn();
    }
    private fun chooseBtn(){
        btn_click?.setBackgroundResource(R.drawable.background_btn_choose_color);
        isClickBtn = true;
        valueChoose = btn_click?.text.toString();
        if(userAnswerList.size - 1 < i){
            userAnswerList.add(valueChoose)
        }
        else if(userAnswerList.size - 1 >= i){
            userAnswerList[i] = valueChoose
        }
    }
    private fun resetChoose(){
        binding.btn0.setBackgroundResource(R.drawable.background_btn_choose);
        binding.btn1.setBackgroundResource(R.drawable.background_btn_choose);
        binding.btn2.setBackgroundResource(R.drawable.background_btn_choose);
        binding.btn3.setBackgroundResource(R.drawable.background_btn_choose);
    }
    private fun getQuestion(i:Int){
        var rnds = (0..3).random()
        val question = data?.get(i)
//        wid = question?.words?.wid!!
        binding.questionIndicatorTextview.text = "Question ${i+1}/ ${data!!.size} "
        binding.questionProgressIndicator.progress =
            ( i.toFloat() / data!!.size.toFloat() * 100 ).toInt()
        if(rnds == 1) binding.questionTextview.text = question!!.words.endesc
        else if(rnds == 2) binding.questionTextview.text = "Từ nào có nghĩa: " + question!!.words.meaning + "\n Phiên âm /" + question.words.pronun +"/"
        else binding.questionTextview.text = question!!.words.viedesc
        if(questionList.size > i){
            binding.questionTextview.text = questionList[i]
        }else{
            questionList.add(binding.questionTextview.text.toString())
            dbAnswerList.add(question.words.word)
        }
        binding.btn0.text = question.answerA
        binding.btn1.text = question.answerB
        binding.btn2.text = question.answerC
        binding.btn3.text = question.answerD

        if(userAnswerList.size - 1 >= i){
            val value = userAnswerList[i]
            if(binding.btn0.text == value){
                ClickChoose(binding.btn0)
            }
            if(binding.btn1.text == value){
                ClickChoose(binding.btn1)
            }
            if(binding.btn2.text == value){
                ClickChoose(binding.btn2)
            }
            if(binding.btn3.text == value){
                ClickChoose(binding.btn3)
            }
        }
    }
    private fun startTimer(){
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)

            }
            override fun onFinish() {
                //Finish the quiz
            }
        }.start()
    }
}