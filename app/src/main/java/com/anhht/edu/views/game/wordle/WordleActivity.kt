package com.anhht.edu.views.game.wordle

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityLearnBinding
import com.anhht.edu.databinding.ActivityWordleBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class WordleActivity : AppCompatActivity() {
    private lateinit var texts:  MutableList<MutableList<TextView>>
    private val rowCount = 6
    private val colCount = 5
    private var countGames = 0
    private var countWins = 0
    private lateinit var gameCore: GameCore
    private lateinit var binding: ActivityWordleBinding
    @SuppressLint("ResourceAsColor")
    private fun popUpShow(word: String, noti: String){
        val dialogBinding = layoutInflater.inflate(R.layout.popup_wordle, null, false);
//        if(noti == "YOU LOSE"){
//            dialogBinding.findViewById<CardView>(R.id.txtWord).setCardBackgroundColor(R.color.red)
//        }else{
//            dialogBinding.findViewById<CardView>(R.id.txtWord).setCardBackgroundColor(R.color.blue)
//        }
        val txtWord = dialogBinding.findViewById<TextView>(R.id.txtWordDesc)
        txtWord.text = word
        val txtnoti = dialogBinding.findViewById<TextView>(R.id.textView2)
        txtnoti.text = noti
        val myDialog = Dialog(this);
        myDialog.setContentView(dialogBinding)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.setCancelable(true)
        myDialog.show()
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordleBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_wordle)
        setContentView(binding.root)
        gameCore = GameCore(rowCount)
        initTexts()
        setEventListeners()
        newRound()
        binding.wordleBack.setOnClickListener {
            finish()
        }
    }

    private fun setEventListeners() {
        for (c in 90 downTo 65) {
            val resID = resources.getIdentifier("button${c.toChar()}", "id", packageName)
            val btn = findViewById<Button>(resID)
            btn.setOnClickListener {
                if (gameCore.isPouse()) {
                    gameCore.startOver()
                    newRound()
                }
                val row = gameCore.getCurRow()
                val col = gameCore.getCurCol()
                if (gameCore.setNextChar(c.toChar())) {
                    texts[row][col].text = c.toChar().toString()
                }
            }
        }
        val btnEnter = findViewById<Button>(R.id.buttonEnter)
        btnEnter.setOnClickListener {
            val row = gameCore.getCurRow()
            if (gameCore.enter()) {
                for (col in 0 until colCount) {
                    val a: Animation = AnimationUtils.loadAnimation(this, R.anim.wordle_anim)
                    val a2: Animation = AnimationUtils.loadAnimation(this, R.anim.wordle_anim2)
                    a.reset()
                    a2.reset()
                    val id = when (gameCore.validateChar(row, col)) {
                        gameCore.IN_WORD -> {
                            R.drawable.background_letter_in_word
                        }
                        gameCore.IN_PLACE -> {
                            R.drawable.background_letter_in_place
                        }
                        else -> {
                            R.drawable.background_letter_not_in
                        }
                    }
                    val time = 300 * col
                    Handler().postDelayed({
                        texts[row][col].background = ContextCompat.getDrawable(this, id)
                        texts[row][col].clearAnimation()
                        texts[row][col].startAnimation(a2)
                        Handler().postDelayed({
                            texts[row][col].clearAnimation()
                            texts[row][col].startAnimation(a)
                        },300)
                    }, time.toLong())
                }
                if (gameCore.getResult()) {
                    countWins++
                    Handler().postDelayed({
                        popUpShow(gameCore.getFinalWord(), "CONGRATULATION")
                        gameCore.startOver()
                        newRound()
                    }, 1600)
                }else if(gameCore.isPouse()){
                    Handler().postDelayed({
                        popUpShow(gameCore.getFinalWord(), "YOU LOSE")
                        gameCore.startOver()
                        newRound()
                    }, 1600)}
            }else{
                Toast.makeText(applicationContext, "Từ không tồn tại trong hệ thống!!!", Toast.LENGTH_SHORT).show();
            }

        }

        val btnErase = findViewById<Button>(R.id.buttonErase)
        btnErase.setOnClickListener {
            if (gameCore.isPouse()) {
                gameCore.startOver()
                newRound()
            }
            gameCore.erase()
            val row = gameCore.getCurRow()
            val col = gameCore.getCurCol()
            texts[row][col].text = " "
        }
    }

    private fun initTexts() {
        texts = MutableList(rowCount) { mutableListOf() }
        for (row in 0 until rowCount) {
            for (col in 0 until colCount) {
                val resID =
                    resources.getIdentifier("text${col + 1}col${row + 1}row", "id", packageName)
                texts[row].add(findViewById(resID))
            }
        }
    }

    private fun newRound() {
        gameCore.setWord()
        for (row in 0 until rowCount) {
            for (col in 0 until colCount) {
                texts[row][col].background = ContextCompat.getDrawable(this,  R.drawable.background_letter_border)
                texts[row][col].text = " "
            }
        }

        val textGames = findViewById<TextView>(R.id.games)
        val textWins = findViewById<TextView>(R.id.wins)
        textGames.text = "Games: $countGames"
        textWins.text = "Wins: $countWins"
        countGames++
        Log.e("Word", "=============---- ${gameCore.getFinalWord()}")
    }
}