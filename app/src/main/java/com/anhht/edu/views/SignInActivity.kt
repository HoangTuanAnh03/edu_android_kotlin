package com.anhht.edu.views

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.anhht.edu.R
import com.anhht.edu.config.SessionManager
import com.anhht.edu.model.request.LoginRequest
import com.anhht.edu.databinding.ActivitySignInBinding
import com.anhht.edu.model.request.RegisterRequest
import com.anhht.edu.repository.AuthApiService
import com.anhht.edu.utils.ValidateDataUtil
import com.anhht.edu.utils.setProgressDialog
import com.anhht.edu.views.forgetpassword.ForgetPasswordActivity
import com.anhht.edu.views.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private var apiService = AuthApiService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sessionManager = SessionManager(this)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
//        if (sessionManager.fetchStateLogin()=="false") {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        binding.redirectSignUp.text = Html.fromHtml(
            "<font color=${Color.BLACK}>Don't have an account? </font>" + "<font color=${Color.BLUE}> Sign up.</font>"
        )

        if (sessionManager.fetchStateRememberPassword() == "true") {
            binding.email.editText!!.setText(sessionManager.fetchEmail())
            binding.password.editText!!.setText(sessionManager.fetchPassWord())
            binding.switchRemember.isChecked = true
        }

        binding.redirectSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.emailInput.setOnClickListener {
            binding.email.error = null
        }

        binding.passwordInput.setOnClickListener {
            binding.password.isErrorEnabled = false
            binding.password.error = null
        }

        binding.forgetPassword.setOnClickListener {
            intent = Intent(this, ForgetPasswordActivity::class.java)
            val option = ActivityOptions.makeCustomAnimation(this, R.anim.right_in, R.anim.left_out).toBundle()
            startActivity(intent, option)
            finish()
        }

        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }

        binding.signInBtn.setOnClickListener {
            if (validField()) {
                val loginRequest = LoginRequest(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                )

                apiService.signIn(loginRequest) {
                    if (it != null) {
                        if (it.status == "OK") {

                            Toast.makeText(
                                this@SignInActivity,
                                "Login Success" + sessionManager.fetchAuthAccessToken(),
                                Toast.LENGTH_SHORT
                            ).show()
                            sessionManager.saveAuthAccessToken(it.data.tokens.accessToken)
                            sessionManager.saveAuthRefreshToken(it.data.tokens.refreshToken)
                            sessionManager.saveStateLogin("true")

                            if (binding.switchRemember.isChecked) {
                                sessionManager.saveEmail(binding.email.editText!!.text.toString())
                                sessionManager.savePassWord(binding.password.editText!!.text.toString())
                                sessionManager.saveStateRememberPassword("true")
                            } else {
                                sessionManager.saveEmail("")
                                sessionManager.savePassWord("")
                                sessionManager.saveStateRememberPassword("false")
                            }

                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else Toast.makeText(
                            this@SignInActivity,
                            "Login Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun validField(): Boolean {
        if (binding.email.editText?.text.toString().trim().isEmpty()) {
            binding.email.error = "Email not empty!"
            return false;
        }
        if (!ValidateDataUtil.isEmail(binding.email.editText?.text.toString().trim())) {
            binding.email.error = "Invalid email format"
            return false;
        }

        if (binding.password.editText?.text.toString().isEmpty()) {
            binding.password.isErrorEnabled = true
            binding.password.error = "Password not empty!"
            return false;
        }

        if (!ValidateDataUtil.isPassWord(binding.password.editText?.text.toString())) {
            binding.password.isErrorEnabled = true
            binding.password.error = "Password must have at least 8 characters"
            return false;
        }
        return true;
    }


    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val sessionManager = SessionManager(this)
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
                                    val processDialog =
                                        setProgressDialog(this, "Sent to your email...")

                                    val uid = user.uid.slice(IntRange(0, 19))

                                    apiService.exitEmail(user.email.toString()) { it1 ->
                                        if (it1?.data.toString() == "true") {
                                            apiService.signIn(
                                                LoginRequest(
                                                    user.email, uid
                                                )
                                            ) { result ->
                                                if (result?.status.toString() == "OK") {
                                                    sessionManager.saveEmail(user.email.toString())
                                                    sessionManager.saveAuthAccessToken(result!!.data.tokens.accessToken)
                                                    sessionManager.saveAuthRefreshToken(result.data.tokens.refreshToken)
                                                    sessionManager.saveStateLogin("true")

                                                    startActivity(
                                                        Intent(
                                                            this,
                                                            HomeActivity::class.java
                                                        )
                                                    )
                                                    finish()
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Please sign in with email and password",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                            }
                                        }
                                        if (it1?.data.toString() == "false") {
                                            processDialog.show()
                                            apiService.register(
                                                RegisterRequest(
                                                    user.email.toString(),
                                                    user.displayName.toString(),
                                                    uid
                                                )
                                            ) { resultRegister ->
                                                processDialog.show()
                                                if (resultRegister?.status.toString() == "OK") {
                                                    processDialog.hide()
                                                    val bundle = Bundle().apply {
                                                        putString("email", user.email)
                                                        putString("password", uid)
                                                        putString("fullname", user.displayName)
                                                    }
                                                    startActivity(
                                                        Intent(
                                                            this,
                                                            SignUpActivity::class.java
                                                        ).putExtras(bundle)
                                                    )
                                                    finish()
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
}