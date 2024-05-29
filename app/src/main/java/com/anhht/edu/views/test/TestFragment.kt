package com.anhht.edu.views.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentTestBinding
import com.anhht.edu.views.learn.LearnActivity

class TestFragment : Fragment() {
    lateinit var binding: FragmentTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(layoutInflater)
        binding.testStart.setOnClickListener{
            var intent =
            activity?.startActivity(Intent(requireContext(), VocabTestActivity::class.java))
        }
        return binding.root
    }


}