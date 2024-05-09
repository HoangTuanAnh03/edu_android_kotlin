package com.anhht.edu.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityHomeBinding
import com.anhht.edu.views.Adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val viewPager : ViewPager = binding.viewPager

        val viewPagerAdaper = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

        viewPager.adapter = viewPagerAdaper
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        navView.menu.findItem(R.id.navigation_learn).setChecked(true)
                    }
                    1 -> {
                        navView.menu.findItem(R.id.navigation_test).setChecked(true)
                    }
                    2 -> {
                        navView.menu.findItem(R.id.navigation_reward).setChecked(true)
                    }
                    3 -> {
                        navView.menu.findItem(R.id.navigation_game).setChecked(true)
                    }
                    else -> {
                        navView.menu.findItem(R.id.navigation_learn).setChecked(true)
                    }
                }
            }

        })
        navView.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_learn -> {
                        viewPager.currentItem = 0
                    }
                    R.id.navigation_test -> {
                        viewPager.currentItem = 1
                    }
                    R.id.navigation_reward -> {
                        viewPager.currentItem = 2
                    }
                    R.id.navigation_game -> {
                        viewPager.currentItem = 3
                    }
                    else -> {
                        viewPager.currentItem = 0
                    }
                }
                return true
            }
        })
    }
}