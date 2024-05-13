package com.anhht.edu.views.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityWordleBinding
import com.anhht.edu.databinding.FragmentGameBinding
import com.anhht.edu.views.game.wordle.WordleActivity
import com.anhht.edu.views.learn.LearnActivity

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        binding.wordle.setOnClickListener{
            var intent = Intent(requireContext(), WordleActivity::class.java)
            activity?.startActivity(intent)
        }

        return binding.root

    }

}