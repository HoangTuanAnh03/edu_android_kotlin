package com.anhht.edu.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.views.signup.NotificationFragment

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        val notificationFragment = NotificationFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, notificationFragment, "userFrag")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setReorderingAllowed(true)
            addToBackStack("userFrag")
            commit()
        }
    }
}