package com.anhht.edu.views.forgetpassword

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentConfirmBinding
import com.anhht.edu.model.request.ChangePasswordRequest
import com.anhht.edu.repository.AuthApiService


class ConfirmFragment : Fragment() {
    private lateinit var binding: FragmentConfirmBinding
    private val apiService = AuthApiService()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmBinding.inflate(inflater, container, false)
        handlerRemoveError()

        binding.btnUpdate.setOnClickListener {
            if (validateInput()) {
                val changePasswordRequest = ChangePasswordRequest(
                    requireArguments().getString("email")!!,
                    requireArguments().getString("otp")!!,
                    binding.password.editText?.text.toString()
                )
                apiService.changePassword(changePasswordRequest) {
                    if (it?.status.toString() == "OK") {
                        val notificationFragment = NotificationFragment()

                        activity?.supportFragmentManager!!.beginTransaction().apply {
                            replace(
                                R.id.forgetPasswordLayout,
                                notificationFragment,
                                "notificationFrag"
                            )
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            setReorderingAllowed(true)
                            addToBackStack("notificationFrag")
                            activity?.supportFragmentManager!!.popBackStackImmediate()
                            commit()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Time to change new password has expired. Please resend the OTP",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            activity?.supportFragmentManager!!.popBackStackImmediate()
                        }, 2000)
                    }
                }
            }
        }

        binding.passwordInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (binding.password.editText?.text.toString().length in 1..7) {
                    binding.password.error = "Password must have at least 8 characters"
                }
            }
        }

        return binding.root
    }

    private fun validateInput(): Boolean {
        var result = true
        if (binding.password.editText?.text.toString().trim().length < 8) {
            binding.password.error = "Password not empty"
            result = false
        }

        if (binding.password.editText?.text.toString().trim().isEmpty()) {
            binding.password.error = "Password not empty"
            result = false
        }

        if (binding.confirmPassword.editText?.text.toString().trim().isEmpty()) {
            binding.confirmPassword.error = "Confirm Password not empty"
            return false
        }

        if (binding.password.editText!!.text.toString() != binding.confirmPassword.editText!!.text.toString()) {
            binding.confirmPassword.error = "Password and Confirm Password not match"
            return false
        }

        return result
    }

    private fun handlerRemoveError() {
        binding.password.setOnClickListener {
            binding.password.error = null
        }
        binding.passwordInput.setOnClickListener {
            binding.password.error = null
        }
        binding.confirmPassword.setOnClickListener {
            binding.confirmPassword.error = null
        }
        binding.confirmPasswordInput.setOnClickListener {
            binding.confirmPassword.error = null
        }
    }

}