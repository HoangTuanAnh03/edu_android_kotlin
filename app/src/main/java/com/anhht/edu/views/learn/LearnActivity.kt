package com.anhht.edu.views.learn

import android.app.Dialog
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityLearnBinding
import com.anhht.edu.databinding.PopupFlashcardBinding
import com.anhht.edu.ktx.randomArcAnimation
import com.anhht.edu.ktx.springAnimate
import com.anhht.edu.ktx.toPx
import com.anhht.edu.model.data.Question
import com.anhht.edu.model.data.Topic
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.repository.WordAPIService
import com.anhht.edu.viewmodels.CoinViewModel
import com.anhht.edu.viewmodels.WordViewModel
import com.anhht.edu.views.learn.Animation.Coin
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


class LearnActivity : AppCompatActivity() , Runnable{
    private lateinit var binding: ActivityLearnBinding

    private lateinit var bindingPopupBinding: PopupFlashcardBinding
    private lateinit var wordViewModel: WordViewModel
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinAPIService : CoinAPIService
    private var data: List<Question>?=null
    private var isClickBtn: Boolean = false
    private var btnClick: Button?=null
    private var valueChoose: String = ""
    private var mDialog: Dialog?=null
    private var coinAnimation = false
    private var mediaPlayer: MediaPlayer?=null
    private var wid:Int = 0

    //animation
    private val handler = Handler(Looper.getMainLooper())
    private var coinMaxCount = 0
    private var coinCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        bindingPopupBinding = PopupFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coinAPIService = CoinAPIService()
        val extras : Bundle? = intent.extras
        val topic : Topic = extras?.get("topic") as Topic

        binding.topicName.text = topic.topic

        mDialog = Dialog(this)
        binding.btnCheck.setOnClickListener{
            if(binding.muitipleChoice.visibility == View.GONE ){
                if(binding.txtAns.text.toString() != ""){
                    isClickBtn = true
                    valueChoose = binding.txtAns.text.toString()
                }
            }
            if(isClickBtn){
                coinViewModel = CoinViewModel(CoinAPIService())
                coinViewModel.postAnswer(this@LearnActivity, valueChoose, wid).observe(this@LearnActivity){d->
                    Log.e("data D", d.toString())
                    if(d.data != 0){
                        Toast.makeText(applicationContext, "Bạn đã nhận được " + d.data + " DATs", Toast.LENGTH_LONG).show()
                        coinAnimation = true
                    }else{
                        Toast.makeText(applicationContext, d.message, Toast.LENGTH_LONG).show()
                    }
                }
                val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
                mDialog!!.setContentView(bindingPopupBinding.root)
                mDialog!!.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
                mDialog!!.window?.setWindowAnimations(R.style.AnimationsForFlashCard)
                mDialog!!.show()
            }
        }

        wordViewModel = WordViewModel(WordAPIService())
        wordViewModel.getWordByTid(this@LearnActivity, topic.tid).observe(this@LearnActivity){question->
            data = question
            Log.e("data", data.toString())
            GlobalScope.launch (Dispatchers.Main){
                getQuestion(0)
            }
        }
        var i = 1
        bindingPopupBinding.nextQuestion.setOnClickListener{
            isClickBtn = false
            if(coinAnimation){
                it?.springAnimate(stiffness = 500f)
                coinMaxCount = 10
                coinCount = 0
                handler.removeCallbacks(this)
                handler.post(this)
                coinAnimation = false
            }

            resetChoose()
            data.let{
                if(i < it?.size!!){
                    getQuestion(i)
                    if(i == it.size.minus(1)){
                        bindingPopupBinding.nextQuestion.text = "Kết thúc"
                    }
                    i++;
                    mDialog!!.hide()
                }else{
                    finish()
                }
            }
        }

        binding.imageBack.setOnClickListener {
            finish()
        }
    }
    override fun run() {
        val edgeHeight by lazy { (binding.parentView.height - 100.toPx + 150.toPx) / 2f }
        val edgeWidth by lazy { (binding.parentView.width + 24.toPx) / 2f }
        if (coinCount < coinMaxCount) {
            val coin = Coin.addTo(binding.parentView)
            coin.randomArcAnimation(0f, -edgeWidth, 0f, -edgeHeight) {
                binding.parentView.removeView(coin)
            }
            handler.postDelayed(this, 50)
            coinCount++
        } else handler.removeCallbacks(this)
    }

    override fun onPause() {
        handler.removeCallbacks(this)
        super.onPause()
    }
    private fun getQuestion(i:Int){
        val type = (0..1).random()
        val rnds = (0..3).random()
        val question = data?.get(i)
        wid = question?.words?.wid!!
        when (rnds) {
            1 -> binding.textQuestion.text = question.words.endesc
            2 -> binding.textQuestion.text = String.format("Từ nào có nghĩa: %s\n Phiên âm /%s/", question.words.meaning, question.words.pronun)
            else -> binding.textQuestion.text = question.words.viedesc
        }
        if(type == 0){
            binding.textAnswer.visibility = View.VISIBLE
            binding.muitipleChoice.visibility = View.GONE
        }else{
            binding.textAnswer.visibility = View.GONE
            binding.muitipleChoice.visibility = View.VISIBLE
        }
        binding.btnChoose1.text = question.answerA
        binding.btnChoose2.text = question.answerB
        binding.btnChoose3.text = question.answerC
        binding.btnChoose4.text = question.answerD


        Picasso.get().load(question.words.photo.toString()).into(bindingPopupBinding.imagePopup);
        bindingPopupBinding.endesc.text = question.words.endesc
        bindingPopupBinding.word.text = question.words.word
        bindingPopupBinding.mean.text = question.words.meaning
        bindingPopupBinding.viedesc.text = question.words.viedesc
        bindingPopupBinding.pronun.text = String.format("/%s/", question.words.pronun)
        bindingPopupBinding.type.text = question.words.entype
        bindingPopupBinding.speek.setOnClickListener{
            question.words.voice.let { it1 -> playAudioFromUrl(it1) }
        }
    }
    public fun ClickChoose(view: View){
        btnClick = view as Button
        if(isClickBtn){
            resetChoose()
        }
        chooseBtn();
    }
    private fun chooseBtn(){
        btnClick?.setTextColor(Color.WHITE)
        btnClick?.setBackgroundResource(R.drawable.background_btn_choose_color);
        isClickBtn = true;
        valueChoose = btnClick?.text.toString();
    }
    private fun resetChoose(){
        binding.txtAns.text.clear()
        binding.btnChoose1.setTextColor(Color.BLACK)
        binding.btnChoose2.setTextColor(Color.BLACK)
        binding.btnChoose3.setTextColor(Color.BLACK)
        binding.btnChoose4.setTextColor(Color.BLACK)
        binding.btnChoose1.setBackgroundResource(R.drawable.background_btn_choose);
        binding.btnChoose2.setBackgroundResource(R.drawable.background_btn_choose);
        binding.btnChoose3.setBackgroundResource(R.drawable.background_btn_choose);
        binding.btnChoose4.setBackgroundResource(R.drawable.background_btn_choose);
    }
    private fun playAudioFromUrl(url:String){
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try{
            mediaPlayer!!.setDataSource(url)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}