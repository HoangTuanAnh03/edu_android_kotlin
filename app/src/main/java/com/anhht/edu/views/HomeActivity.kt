package com.anhht.edu.views

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityHomeBinding
import com.anhht.edu.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.internal.notify

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val viewPager : ViewPager = binding.viewPager
        val viewPagerAdaper = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = viewPagerAdaper

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (currentPage != 0) {
                    viewPager.currentItem = 0
                }else{
                    finishAffinity()
                }
            }
        })
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                currentPage = position
                if(position == 0) navView.menu.findItem(R.id.navigation_learn).setChecked(true)
                else if(position == 1) navView.menu.findItem(R.id.navigation_test).setChecked(true)
                else if(position == 2) navView.menu.findItem(R.id.navigation_reward).setChecked(true)
                else if(position == 3) navView.menu.findItem(R.id.navigation_game).setChecked(true)
                else if(position == 4) navView.menu.findItem(R.id.navigation_profile).setChecked(true)
                else navView.menu.findItem(R.id.navigation_learn).setChecked(true)
            }
        })
        navView.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if(item.itemId == R.id.navigation_learn){
                    viewPager.currentItem = 0
                    currentPage = 0
                }
                else if(item.itemId == R.id.navigation_test){
                    viewPager.currentItem = 1
                    currentPage = 1
                }
                else if(item.itemId == R.id.navigation_reward){
                    viewPager.currentItem = 2
                    currentPage = 2
                }
                else if(item.itemId == R.id.navigation_game){
                    viewPager.currentItem = 3
                    currentPage = 3
                }
                else if(item.itemId == R.id.navigation_profile){
                    viewPager.currentItem = 4
                    currentPage = 4
                }
                else if(item.itemId == R.id.navigation_learn){
                    viewPager.currentItem = 5
                    currentPage = 5
                }else{
                    viewPager.currentItem = 0
                    currentPage = 0
                }
                return true
            }
        })
    }
}