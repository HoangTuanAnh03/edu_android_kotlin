package com.anhht.edu.views.forgetpassword

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentConfirmBinding
import com.anhht.edu.databinding.FragmentNotificationForgetPasswordBinding
import com.anhht.edu.views.SignInActivity


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationForgetPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationForgetPasswordBinding.inflate(inflater, container, false)

        activity?.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.actionbarForgetPassword)?.visibility = View.GONE

        binding.btnSign.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), SignInActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }

}