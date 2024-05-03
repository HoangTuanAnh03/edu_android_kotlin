package com.anhht.edu.views.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (intent.getStringExtra("email") != null) {
            val notificationFragment = NotificationFragment()
            val bundle = Bundle()
            bundle.putString("email", intent.getStringExtra("email"))
            notificationFragment.arguments = bundle

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.register_frame_layout, notificationFragment, "registerFrag")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                setReorderingAllowed(true)
                addToBackStack("notificationFrag")
                commit()
            }
        } else {
            val registerFragment = RegisterFragment()

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.register_frame_layout, registerFragment, "registerFrag")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                setReorderingAllowed(true)
                addToBackStack("registerFrag")
                commit()
            }
        }


    }
}