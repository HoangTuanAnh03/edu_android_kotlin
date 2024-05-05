package com.anhht.edu.views.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentNotificationBinding
import com.anhht.edu.views.SignInActivity

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.materialToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)


        binding.textView.text = getString(R.string.register_email, this.arguments?.getString("email") )

        binding.materialToolbar.setNavigationOnClickListener {
            val manager = requireActivity().supportFragmentManager



            if (manager.findFragmentByTag("registerFrag") != null){
                manager.beginTransaction().apply {
                    remove(this@NotificationFragment)
                    commit()
                }
                manager.popBackStack()
            } else {
                activity?.startActivity(Intent(requireContext(), SignInActivity::class.java))
                activity?.finish()
            }
        }

        binding.btnGoToSignIn.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), SignInActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }

}