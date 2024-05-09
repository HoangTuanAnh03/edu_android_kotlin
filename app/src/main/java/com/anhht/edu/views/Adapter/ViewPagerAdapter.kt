package com.anhht.edu.views.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.anhht.edu.views.game.GameFragment
import com.anhht.edu.views.learn.LearnFragment

class ViewPagerAdapter(fm: FragmentManager, behavior:Int) : FragmentStatePagerAdapter(fm, behavior) {
    override fun getCount(): Int {
        return 4
    }
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> LearnFragment()
            else -> GameFragment()
        }
    }
}