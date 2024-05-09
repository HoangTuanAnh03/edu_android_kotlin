package com.anhht.edu.views.learn

import android.app.Dialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityLearnBinding
import com.anhht.edu.databinding.PopupFlashcardBinding
import com.anhht.edu.model.data.Question
import com.anhht.edu.model.data.Topic
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.repository.WordAPIService
import com.anhht.edu.viewmodels.CoinViewModel
import com.anhht.edu.viewmodels.WordViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class LearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding
    private lateinit var bindingPopupBinding: PopupFlashcardBinding
    private lateinit var wordViewModel: WordViewModel
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinAPIService : CoinAPIService
    private var data: List<Question>?=null
    private var isClickBtn: Boolean = false
    private var btn_click: Button?=null
    private var valueChoose: String = ""
    private var mDialog: Dialog?=null
    private var mediaPlayer: MediaPlayer?=null
    private var wid:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        bindingPopupBinding = PopupFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coinAPIService = CoinAPIService()
        val extras : Bundle? = intent.extras
        val topic : Topic = extras?.get("topic") as Topic
        mDialog = Dialog(this)

        binding.btnNext.setOnClickListener{
            if(isClickBtn){
                val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
                mDialog!!.setContentView(bindingPopupBinding.root)
                mDialog!!.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
                mDialog!!.window?.setWindowAnimations(R.style.AnimationsForFlashCard)
                mDialog!!.show()
                coinViewModel = CoinViewModel(CoinAPIService())
                coinViewModel.postAnswer(valueChoose, wid).observe(this@LearnActivity){d->
                    Log.e("data D", d.toString())
                    if(d.data != 0){
                        //Snackbar.make(this,"Bạn đã nhận được " + d.data + " DATs", Snackbar.ANIMATION_MODE_FADE )
                        Toast.makeText(applicationContext, "Bạn đã nhận được " + d.data + " DATs", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext, d.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        wordViewModel = WordViewModel(WordAPIService())
        wordViewModel.getWordByTidTest(topic.tid).observe(this@LearnActivity){question->
            data = question
            Log.e("data", data.toString())
            GlobalScope.launch (Dispatchers.Main){
                getQuestion(0)
            }
        }

        var i = 1
        bindingPopupBinding.nextQuestion.setOnClickListener{
            isClickBtn = false
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
    private fun getQuestion(i:Int){
        var rnds = (0..3).random()
        val question = data?.get(i)
        wid = question?.words?.wid!!
        if(rnds == 1) binding.textQuestion.text = question.words.endesc
        else if(rnds == 2) binding.textQuestion.text = "Từ nào có nghĩa: " + question.words.meaning + "\n Phiên âm /" + question.words.pronun +"/"
        else binding.textQuestion.text = question.words.viedesc

        binding.btnChoose1.text = question.answerA
        binding.btnChoose2.text = question.answerB
        binding.btnChoose3.text = question.answerC
        binding.btnChoose4.text = question.answerD


        Picasso.with(this).load(question.words.photo.toString()).into(bindingPopupBinding.imagePopup);
        bindingPopupBinding.endesc.text = question.words.endesc
        bindingPopupBinding.word.text = question.words.word
        bindingPopupBinding.mean.text = question.words.meaning
        bindingPopupBinding.viedesc.text = question.words.viedesc
        bindingPopupBinding.pronun.text = "/" + question.words.pronun + "/"
        bindingPopupBinding.type.text = question.words.entype
        bindingPopupBinding.speek.setOnClickListener{
            question.words.voice.let { it1 -> playAudioFromUrl(it1) }
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
    }
    private fun resetChoose(){
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