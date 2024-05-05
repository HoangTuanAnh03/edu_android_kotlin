package com.anhht.edu.views.forgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityForgetPasswordBinding
import com.anhht.edu.views.SignInActivity

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.actionbarForgetPassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val emailFragment = EmailFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.forgetPasswordLayout, emailFragment, "emailFrag")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setReorderingAllowed(true)
            addToBackStack("emailFrag")
            commit()
        }

        binding.actionbarForgetPassword.setNavigationOnClickListener {
            val index = supportFragmentManager.backStackEntryCount - 1
            val backEntry = supportFragmentManager.getBackStackEntryAt(index)
            val tag = backEntry.name;
            val fragment = supportFragmentManager.findFragmentByTag(tag)

            if (tag == "emailFrag") {
                intent = Intent(this, SignInActivity::class.java)
//                val option = ActivityOptions.makeCustomAnimation(
//                    this, R.anim.trans_right_in, R.anim.trans_right_out
//                ).toBundle()
                startActivity(intent)
                finish()
            } else {
                supportFragmentManager.beginTransaction().apply {
                    remove(fragment!!)
                    commit()
                }
                supportFragmentManager.popBackStack()
            }
        }

    }
}