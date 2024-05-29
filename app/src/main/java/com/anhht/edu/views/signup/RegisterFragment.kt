package com.anhht.edu.views.signup

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.anhht.edu.R
import com.anhht.edu.config.SessionManager
import com.anhht.edu.databinding.FragmentRegisterBinding
import com.anhht.edu.model.request.LoginRequest
import com.anhht.edu.model.request.RegisterRequest
import com.anhht.edu.repository.AuthApiService
import com.anhht.edu.utils.ValidateDataUtil
import com.anhht.edu.utils.setProgressDialog
import com.anhht.edu.views.BtnLoadingProgressbar
import com.anhht.edu.views.MainActivity
import com.anhht.edu.views.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var apiService = AuthApiService()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.redirectSignIn.text = Html.fromHtml(
            "<font color=${Color.BLACK}>Have an account? </font>" + "<font color=${Color.BLUE}> Sign in.</font>"
        )

        binding.redirectSignIn.setOnClickListener {
            startActivity(Intent(requireContext(), SignInActivity::class.java))
            activity?.finish()
        }

        binding.btnContinue.setOnClickListener {
            handlerDisplayField(1)
        }

        binding.btnBack.setOnClickListener {
            handlerDisplayField(0)
        }


        binding.passwordInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (binding.password.editText?.text.toString().length in 1..7) {
                    binding.password.error = "Password must have at least 8 characters"
                }
            }
        }

        val arr: Array<String> = arrayOf("Email", "FullName", "Password")
        binding.stepView.setSteps(arr.toList());
        handlerRemoveError()

        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRegister = view.findViewById<View>(R.id.btn_register)!!


        btnRegister.setOnClickListener {
            if (validPassword()) {

                val progressbar = BtnLoadingProgressbar(it)
                progressbar.setLoading()

                val registerRequest = RegisterRequest(
                    binding.email.editText!!.text.toString().trim(),
                    binding.fullname.editText!!.text.toString(),
                    binding.confirmPassword.editText!!.text.toString()
                )

                apiService.register(registerRequest) { resultRegister ->
                    progressbar.reset()
                    if (resultRegister?.status.toString() == "CONFLICT") {
                        Toast.makeText(
                            requireContext(),
                            "Email already exists!!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    if (resultRegister?.status.toString() == "OK") {
                        val notificationFragment = NotificationFragment()
                        val bundle = Bundle()
                        bundle.putString("email", binding.email.editText!!.text.toString())
                        notificationFragment.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.register_frame_layout,
                                notificationFragment,
                                "notificationFrag"
                            )
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            setReorderingAllowed(true)
                            addToBackStack("notificationFrag")
                            commit()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Register Fail!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun validPassword(): Boolean {
        var result = true

        if (binding.password.editText!!.text.toString() != binding.confirmPassword.editText!!.text.toString()) {
            binding.confirmPassword.error = "Password and Confirm Password not match"
            result = false
        }

        if (binding.confirmPassword.editText?.text.toString().trim().isEmpty()) {
            binding.confirmPassword.error = "Confirm Password not empty"
            result = false
        }

        if (binding.password.editText?.text.toString().trim().isEmpty()) {
            binding.password.error = "Password not empty"
            result = false
        }
        return result
    }


    private fun handlerRemoveError() {
        binding.email.setOnClickListener {
            binding.email.error = null
        }
        binding.emailInput.setOnClickListener {
            binding.email.error = null
        }
        binding.fullname.setOnClickListener {
            binding.fullname.error = null
        }
        binding.fullnameInput.setOnClickListener {
            binding.fullname.error = null
        }
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


    private fun handlerDisplayField(type: Int) {
        val currentStep = binding.stepView.currentStep
        val btnRegisterTV = view?.findViewById<TextView>(R.id.btn_loading_layout_tv)!!
        val btnRegister = view?.findViewById<View>(R.id.btn_register)!!


        when (binding.stepView.currentStep + 1) {
            1 -> {
                if (type == 0) {
                    binding.fullname.visibility = View.GONE
                    binding.email.visibility = View.VISIBLE
                    binding.password.visibility = View.GONE
                    binding.confirmPassword.visibility = View.GONE
                }
                if (type == 1) {
                    if (binding.email.editText?.text.toString().trim().isEmpty()) {
                        binding.email.error = "Email not empty!"
                        return
                    }
                    if (!ValidateDataUtil.isEmail(binding.email.editText?.text.toString().trim())
                    ) {
                        binding.email.error = "Invalid email format"
                        return
                    }

                    apiService.exitEmail(binding.email.editText!!.text.toString()) {
                        if (it?.data.toString() == "true") {
                            binding.email.error = "Email already exists!"
                        } else {
//                            currentStep++
                            binding.stepView.go(currentStep + 1, true);

                            binding.fullname.visibility = View.VISIBLE
                            binding.email.visibility = View.GONE
                            binding.password.visibility = View.GONE
                            binding.confirmPassword.visibility = View.GONE

                            binding.btnBack.visibility = View.VISIBLE
                        }
                    }
                }
            }

            2 -> {
                // full name
                if (type == 0) {
//                    currentStep--
                    binding.stepView.go(currentStep - 1, true);

                    binding.fullname.visibility = View.GONE
                    binding.email.visibility = View.VISIBLE
                    binding.password.visibility = View.GONE
                    binding.confirmPassword.visibility = View.GONE

                    binding.btnBack.visibility = View.GONE

                }
                if (type == 1) {
                    if (binding.fullname.editText?.text.toString().trim().isEmpty()) {
                        binding.fullname.error = "FullName not empty!"
                        return
                    }
//                    currentStep++
                    binding.stepView.go(currentStep + 1, true);

                    binding.fullname.visibility = View.GONE
                    binding.email.visibility = View.GONE
                    binding.password.visibility = View.VISIBLE
                    binding.confirmPassword.visibility = View.VISIBLE

                    binding.btnContinue.text = "Register"

                    binding.linearLayout.visibility = View.GONE
                    binding.googleBtn.visibility = View.GONE

                    binding.btnContinue.visibility = View.GONE
                    btnRegisterTV.text = "Register"
                    btnRegister.visibility = View.VISIBLE


                }
            }

            3 -> {
                // pass
                if (type == 0) {
//                    currentStep--
                    binding.stepView.go(currentStep - 1, false)

                    binding.fullname.visibility = View.VISIBLE
                    binding.email.visibility = View.GONE
                    binding.password.visibility = View.GONE
                    binding.confirmPassword.visibility = View.GONE

                    binding.btnContinue.text = "Continue"

                    binding.linearLayout.visibility = View.VISIBLE
                    binding.googleBtn.visibility = View.VISIBLE

                    binding.btnContinue.visibility = View.VISIBLE
                    btnRegister.visibility = View.GONE
                }
            }
        }
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val sessionManager = SessionManager(requireContext())
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    if (task.isSuccessful) {
                        val account: GoogleSignInAccount? = task.result
                        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = auth.currentUser!!

                                    val uid = user.uid.slice(IntRange(0, 19))

                                    apiService.exitEmail(user.email.toString()) { it1 ->
                                        if (it1?.data.toString() == "true") {
                                            apiService.signIn(
                                                LoginRequest(
                                                    user.email, uid
                                                )
                                            ) { result ->
                                                if (result?.status.toString() == "OK") {
                                                    sessionManager.saveStateRememberPassword("false");
                                                    sessionManager.saveUserName(user.displayName.toString())
                                                    sessionManager.saveEmail(user.email.toString())
                                                    sessionManager.saveAuthAccessToken(result!!.data.tokens.accessToken)
                                                    sessionManager.saveAuthRefreshToken(result.data.tokens.refreshToken)
                                                    sessionManager.saveStateLogin("true")

                                                    activity?.startActivity(
                                                        Intent(
                                                            requireContext(),
                                                            MainActivity::class.java
                                                        )
                                                    )
                                                    activity?.finish()
                                                } else {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Email was registered",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                            }
                                        }
                                        if (it1?.data.toString() == "false") {
                                            val processDialog =
                                                setProgressDialog(
                                                    requireContext(),
                                                    "Sent to your email..."
                                                )
                                            processDialog.show()
                                            apiService.register(
                                                RegisterRequest(
                                                    user.email.toString(),
                                                    user.displayName.toString(),
                                                    uid
                                                )
                                            ) { resultRegister ->
                                                processDialog.hide()
                                                if (resultRegister?.status.toString() == "OK") {
                                                    val bundle = Bundle().apply {
                                                        putString(
                                                            "email",
                                                            user.email
                                                        )
                                                    }
                                                    processDialog.hide()

                                                    val notificationFragment =
                                                        NotificationFragment()
                                                    notificationFragment.arguments = bundle

                                                    requireActivity().supportFragmentManager.beginTransaction()
                                                        .apply {
                                                            replace(
                                                                R.id.register_frame_layout,
                                                                notificationFragment,
                                                                "notificationFrag"
                                                            )
                                                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                                            setReorderingAllowed(true)
                                                            addToBackStack("notificationFrag")
                                                            commit()
                                                        }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }
                } catch (e: ApiException) {
                    Toast.makeText(
                        requireContext(),
                        "Google sign in failed: ${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
}