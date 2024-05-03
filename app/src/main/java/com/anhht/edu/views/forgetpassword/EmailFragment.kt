package com.anhht.edu.views.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentEmailBinding
import com.anhht.edu.repository.AuthApiService
import com.anhht.edu.utils.ValidateDataUtil
import com.anhht.edu.utils.setProgressDialog


class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding
    private val apiService = AuthApiService()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailBinding.inflate(inflater, container, false)
        handlerRemoveError()


        binding.btnContinue.setOnClickListener {
            if (handlerValidate()) {
                val processDialog = setProgressDialog(requireContext(), "Sent OTP to your email...")
                processDialog.show()
                apiService.forgetPassword(binding.email.editText!!.text.toString()) {it1 ->

                    processDialog.hide()
//                    if (it1?.status.toString() == "OK") {
                        val otpFragment = OtpFragment()
                        val bundle = Bundle()
                        bundle.putString("email", binding.email.editText!!.text.toString())
                        otpFragment.arguments = bundle

                        activity?.supportFragmentManager!!.beginTransaction().apply {
                            replace(R.id.forgetPasswordLayout, otpFragment, "otpFrag")
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            setReorderingAllowed(true)
                            addToBackStack("otpFrag")
                            commit()
                        }
//                    }
                    if (it1?.status.toString() == "BAD_REQUEST") {
                        binding.email.error = "You have entered the wrong OTP more than 5 times. Please wait another 5 minutes"
                    }
                }
            }
        }


        return binding.root
    }

    private fun handlerValidate(): Boolean {
        if (binding.email.editText?.text.toString().trim().isEmpty()) {
            binding.email.error = "Email not empty!"
            return false
        }
        if (!ValidateDataUtil.isEmail(binding.email.editText?.text.toString().trim())
        ) {
            binding.email.error = "Invalid email format"
            return false
        }

        var r = true
        apiService.exitEmail(binding.email.editText!!.text.toString().trim()) { response ->
            if (response?.data.toString() == "false") {
                binding.email.error = "Email doesn't exists!!"
                r = false
            }
        }

        return r
    }

    private fun handlerRemoveError() {
        binding.email.setOnClickListener {
            binding.email.error = null
        }
        binding.emailInput.setOnClickListener {
            binding.email.error = null
        }
    }
}