package com.anhht.edu.views.test

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityVocabTestBinding
import com.anhht.edu.model.data.Question
import com.anhht.edu.repository.WordAPIService
import com.anhht.edu.viewmodels.WordViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VocabTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityVocabTestBinding
    private var data: List<Question>? = null
    private var isClickBtn: Boolean = false
    private var btnClick: Button? = null
    private var valueChoose: String = ""
    private var userAnswerList: ArrayList<String> = ArrayList()
    private var dbAnswerList: ArrayList<String> = ArrayList()
    private var questionList: ArrayList<String> = ArrayList()
    private var time: String = "10"
    var i = 0
    private lateinit var wordViewModel: WordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVocabTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        wordViewModel = WordViewModel(WordAPIService())
        wordViewModel.getTest(this@VocabTestActivity).observe(this@VocabTestActivity) { question ->
            data = question.data
            binding.preBtn.visibility = View.GONE
            time = data!!.size.toString()
            GlobalScope.launch(Dispatchers.Main) {
                getQuestion(0)
                startTimer()
            }
        }


        binding.nextBtn.setOnClickListener {
            if (isClickBtn) {
                isClickBtn = false
                resetChoose()
                i++
                data.let {
                    if (i < it?.size!!) {
                        getQuestion(i)
                        if (i == it.size.minus(1)) {
                            binding.nextBtn.text = String.format("Kết thúc")
                        } else {
                            binding.preBtn.visibility = View.VISIBLE
                        }
                    } else {
                        submitForm()
                    }
                }
            }
        }
        binding.preBtn.setOnClickListener {
            resetChoose()
            data.let {
                if (i > 0) {
                    i -= 1
                    getQuestion(i)
                    binding.nextBtn.text = String.format("Tiếp Theo")
                    if (i == 0) {
                        binding.preBtn.visibility = View.GONE
                    } else {
                        binding.preBtn.visibility = View.VISIBLE
                    }
                } else {
//                        finish()
                }
            }
        }
        binding.testBack.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.apply {
                setTitle("Bạn có chắc chắn muốn thoát không?")
                setCancelable(true)
                setPositiveButton("Có") { _: DialogInterface?, _: Int ->
                    finish()
                }
                setNegativeButton("Không") { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
        }
    }

    fun clickChoose(view: View) {
        btnClick = view as Button
        if (isClickBtn) {
            resetChoose()
        }
        chooseBtn()
    }

    private fun chooseBtn() {
        btnClick?.setBackgroundResource(R.drawable.background_btn_choose_color)
        btnClick?.setTextColor(Color.WHITE)
        isClickBtn = true
        valueChoose = btnClick?.text.toString()
        if (userAnswerList.size - 1 < i) {
            userAnswerList.add(valueChoose)
        } else if (userAnswerList.size - 1 >= i) {
            userAnswerList[i] = valueChoose
        }
    }

    private fun resetChoose() {
        binding.btn0.setTextColor(Color.BLACK)
        binding.btn1.setTextColor(Color.BLACK)
        binding.btn2.setTextColor(Color.BLACK)
        binding.btn3.setTextColor(Color.BLACK)
        binding.btn0.setBackgroundResource(R.drawable.background_btn_choose)
        binding.btn1.setBackgroundResource(R.drawable.background_btn_choose)
        binding.btn2.setBackgroundResource(R.drawable.background_btn_choose)
        binding.btn3.setBackgroundResource(R.drawable.background_btn_choose)
    }

    private fun getQuestion(i: Int) {

        val rnds = (0..3).random()
        val question = data?.get(i)
        binding.questionIndicatorTextview.text = String.format("Question ${i + 1}/ ${data!!.size} ")
        binding.questionProgressIndicator.progress =
            (i.toFloat() / data!!.size.toFloat() * 100).toInt()
        when ((0..3).random()) {
            1 -> binding.questionTextview.text = question!!.words.endesc
            2 -> binding.questionTextview.text = String.format(
                "Từ nào có nghĩa: %s\n Phiên âm /%s/",
                question!!.words.meaning,
                question.words.pronun
            )

            else -> binding.questionTextview.text = question!!.words.viedesc
        }
        if (questionList.size > i) {
            binding.questionTextview.text = questionList[i]
        } else {
            questionList.add(binding.questionTextview.text.toString())
            dbAnswerList.add(question.words.word)
        }
        binding.btn0.text = question.answerA
        binding.btn1.text = question.answerB
        binding.btn2.text = question.answerC
        binding.btn3.text = question.answerD

        if (userAnswerList.size - 1 >= i) {
            val value = userAnswerList[i]
            if (binding.btn0.text == value) {
                clickChoose(binding.btn0)
            }
            if (binding.btn1.text == value) {
                clickChoose(binding.btn1)
            }
            if (binding.btn2.text == value) {
                clickChoose(binding.btn2)
            }
            if (binding.btn3.text == value) {
                clickChoose(binding.btn3)
            }
        }
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 30 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)

            }

            override fun onFinish() {
                for (i in questionList.size..<data!!.size) {
                    when ((0..3).random()) {
                        1 -> questionList.add(data!![i].words.endesc)
                        2 -> questionList.add("Từ nào có nghĩa: " + data!![i].words.meaning + "\n Phiên âm /" + data!![i].words.pronun + "/")
                        else -> questionList.add(data!![i].words.viedesc)
                    }
                    dbAnswerList.add(data!![i].words.word)
                }
                for (i in userAnswerList.size..<data!!.size) {
                    userAnswerList.add("")
                }
                submitForm()
            }
        }.start()
    }

    fun submitForm() {
        var matchCount = 0
        for (i in userAnswerList.indices) {
            if (userAnswerList[i] == dbAnswerList[i]) {
                matchCount++
            }
        }
        val intent = Intent(this@VocabTestActivity, TestResultsActivity::class.java)
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