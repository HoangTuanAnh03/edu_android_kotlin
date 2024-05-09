package com.anhht.edu.views.learn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityLearnBinding

class LearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val levelFragment = LevelFragment()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.learnLayout, levelFragment, "levelFrag")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setReorderingAllowed(true)
            addToBackStack("emailFrag")
            commit()
        }
//        binding.actionbarLearn.setNavigationOnClickListener {
//            val index = supportFragmentManager.backStackEntryCount - 1
//            val backEntry = supportFragmentManager.getBackStackEntryAt(index)
//            val tag = backEntry.name;
//            val fragment = supportFragmentManager.findFragmentByTag(tag)
//            Log.d("Frag", fragment?.tag.toString())
//            if (tag == "levelFrag") {
//                intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                supportFragmentManager.beginTransaction().apply {
//                    remove(fragment!!)
//                    commit()
//                }
//                supportFragmentManager.popBackStack()
//            }
//        }
    }
}