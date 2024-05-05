package com.anhht.edu.views.forgetpassword

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.databinding.FragmentOtpBinding
import com.anhht.edu.repository.AuthApiService
import com.anhht.edu.utils.setProgressDialog
import java.util.concurrent.TimeUnit

class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private val apiService = AuthApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(inflater, container, false)

        val email = this.requireArguments().getString("email")!!

        binding.guideOtp.text = getString(R.string.guide_otp, email)


        val timer = object : CountDownTimer(1000 * 60 * 1, 1000) {
            override fun onTick(millisUntilFinished: Long) {
//                binding.countdownTime.text = (millisUntilFinished / 1000).toString()
                val hms = String.format(
                    "%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(
                        1
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(
                        1
                    )
                )
                binding.countdownTime.text = "Time remaining " + hms

            }

            override fun onFinish() {
                binding.countdownTime.text = "The OTP has expired.\n Please press the Resent OTP"
//                binding.btnVerifi.isEnabled = false
                binding.btnVerifi.visibility = View.GONE
                binding.error.visibility = View.GONE
                binding.error.text = ""
                binding.pinview.setLineColor(Color.BLUE)
                binding.btnResent.isEnabled = true
            }
        }
        timer.start()

        binding.btnResent.setOnClickListener {

            val processDialog = setProgressDialog(requireContext(), "Resent OTP to your email...")
            processDialog.show()
            apiService.forgetPassword(email) {
                processDialog.hide()
                if (it?.status.toString() == "OK") {
//                    binding.btnVerifi.isEnabled = true
                    binding.btnVerifi.visibility = View.VISIBLE
                    binding.error.visibility = View.GONE
                    binding.error.text = ""
                    binding.pinview.setLineColor(Color.BLUE)
                    timer.start()
                }
                if (it?.status.toString() == "BAD_REQUEST") {
                    binding.error.text =
                        "You have entered the wrong OTP more than 5 times. Please wait another 5 minutes"
                    binding.btnResent.isEnabled = false
                }
            }
        }
        binding.btnVerifi.setOnClickListener {
            if (binding.pinview.text.toString().length == 6) {
                apiService.checkOtp(email, binding.pinview.text.toString()) {
                    if (it?.data.toString() == "true") {
                        val confirmFragment = ConfirmFragment()
                        val bundle = Bundle()
                        bundle.putString("email", email)
                        bundle.putString("otp", binding.pinview.text.toString())
                        confirmFragment.arguments = bundle

                        activity?.supportFragmentManager!!.beginTransaction().apply {
                            replace(R.id.forgetPasswordLayout, confirmFragment, "confirmFrag")
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            setReorderingAllowed(true)
                            addToBackStack("confirmFrag")
                            activity?.supportFragmentManager!!.popBackStackImmediate()
                            commit()
                        }
                    }
                    if (it?.data.toString() == "false") {
                        binding.error.visibility = View.VISIBLE
                        binding.error.text = "The OTP is incorrect."
                        binding.pinview.setLineColor(Color.RED)
                    }

                    if (it?.data.toString() == "FailAttempt") {
                        binding.error.visibility = View.VISIBLE
                        binding.countdownTime.text = ""
                        binding.btnVerifi.visibility = View.GONE
                        timer.cancel()
                        timer.start()
                        binding.btnResent.isEnabled = false
                        binding.error.text =
                            "You have entered the wrong OTP more than 5 times. Please wait another 5 minutes"
                    }
                }
            } else {
                binding.error.visibility = View.VISIBLE
                binding.error.text = "Please fill in the OTP completely"
                binding.pinview.setLineColor(Color.RED)
            }
        }

        binding.pinview.setOnClickListener {
            binding.error.visibility = View.GONE
            binding.error.text = ""
            binding.pinview.setLineColor(Color.BLUE)
        }

        return binding.root
    }
}