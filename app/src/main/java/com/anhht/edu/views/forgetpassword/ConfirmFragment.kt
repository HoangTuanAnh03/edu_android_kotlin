package com.anhht.edu.views.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentConfirmBinding


class ConfirmFragment : Fragment() {
    private lateinit var binding: FragmentConfirmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmBinding.inflate(inflater, container, false)


        validateInput()



        return binding.root
    }

    private fun validateInput(): Boolean {
        if (binding.password.editText?.text.toString().trim().isEmpty()) {
            binding.password.error = "Password not empty"
            return false
        }

        if (binding.confirmPassword.editText?.text.toString().trim().isEmpty()) {
            binding.confirmPassword.error = "Confirm Password not empty"
            return false

        }

        if (binding.password.editText!!.text.toString() != binding.confirmPassword.editText!!.text.toString()) {
            binding.confirmPassword.error = "Password and Confirm Password not match"
            return false
        }

        return true
    }

}