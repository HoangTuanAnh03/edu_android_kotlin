package com.anhht.edu.views.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.anhht.edu.views.BlankFragment
import com.anhht.edu.views.Profile.ProfileFragment
import com.anhht.edu.views.Reward.RewardFragment
import com.anhht.edu.views.game.GameFragment
import com.anhht.edu.views.learn.LearnFragment
import com.anhht.edu.views.test.TestFragment

class ViewPagerAdapter(fm: FragmentManager, behavior:Int) : FragmentStatePagerAdapter(fm, behavior) {
    override fun getCount(): Int {
        return 5
    }
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> LearnFragment()
            1 -> TestFragment()
            2 -> RewardFragment()
            3 -> GameFragment()
            4 -> ProfileFragment()
            else -> BlankFragment()
        }
    }
}