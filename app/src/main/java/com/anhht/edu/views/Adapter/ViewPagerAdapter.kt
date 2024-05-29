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
        if(position == 0) return LearnFragment()
        if(position == 1) return TestFragment()
        if(position == 2) return RewardFragment()
        if(position == 3) return GameFragment()
        if(position == 4) return ProfileFragment()
        return BlankFragment()
    }
}