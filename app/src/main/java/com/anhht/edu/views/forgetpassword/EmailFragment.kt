package com.anhht.edu.views.forgetpassword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentEmailBinding
import com.anhht.edu.repository.AuthApiService
import com.anhht.edu.utils.ValidateDataUtil
import com.anhht.edu.views.BtnLoadingProgressbar


class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding
    private val apiService = AuthApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handlerRemoveError()

        view.findViewById<TextView>(R.id.btn_loading_layout_tv)!!.text = "Continue"

        val btnContinue = view.findViewById<View>(R.id.btn_continue_email)!!

        btnContinue.setOnClickListener {
            if (handlerValidate()) {
                apiService.exitEmail(binding.email.editText!!.text.toString().trim()) { response ->
                    if (response?.data.toString() == "false") {
                        binding.email.error = "Email doesn't exists!!"
                    } else {
                        val progressbar = BtnLoadingProgressbar(it)
                        progressbar.setLoading()
                        apiService.forgetPassword(binding.email.editText!!.text.toString()) { it1 ->
                            progressbar.reset()
                            if (it1?.status.toString() == "OK") {
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
                            }
                            if (it1?.status.toString() == "BAD_REQUEST") {
                                binding.email.error =
                                    "You have entered the wrong OTP more than 5 times. Please wait another 5 minutes"
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handlerValidate(): Boolean {
        var r = true

        if (!ValidateDataUtil.isEmail(binding.email.editText?.text.toString().trim())
        ) {
            binding.email.error = "Invalid email format"
            r = false
        }
        if (binding.email.editText?.text.toString().trim().isEmpty()) {
            binding.email.error = "Email not empty!"
            r = false
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