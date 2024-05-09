package com.anhht.edu.views.learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentLearnBinding
import com.google.android.material.appbar.MaterialToolbar

class LearnFragment() : Fragment() {
    lateinit var binding: FragmentLearnBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLearnBinding.inflate(layoutInflater)

        val levelFragment = LevelFragment()
        val fm: FragmentTransaction = childFragmentManager.beginTransaction()
        fm.apply {
            add(R.id.learnLayoutF, levelFragment, "levelF")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setReorderingAllowed(true)
            addToBackStack("levelF")
            commit()
        }

    }

}