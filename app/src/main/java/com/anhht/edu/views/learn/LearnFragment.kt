package com.anhht.edu.views.learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {
    lateinit var binding: FragmentLearnBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = FragmentLearnBinding.inflate(layoutInflater)

        val levelFragment = LevelFragment()
        val fm: FragmentTransaction = activity?.supportFragmentManager?.beginTransaction()!!
        fm.replace(R.id.learnLayoutF, levelFragment).commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

}