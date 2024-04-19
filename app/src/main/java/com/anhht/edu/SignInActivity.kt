package com.anhht.edu

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.anhht.edu.databinding.ActivitySignInBinding
import com.anhht.edu.test.TestApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.redirectSignUp.text = Html.fromHtml(
            "<font color=${Color.BLACK}>Don't have an account? </font>" + "<font color=${Color.BLUE}> Sign up.</font>"
        )


        binding.signInBtn.setOnClickListener {
//            validField()
            val apiService = RestApiService()
            val userRequest = UserRequest(
                "anh0178866@huce.edu.vn",
                "12345678"
            )
////
//            apiService.signIn(userRequest) {
//                binding.redirectSignUp.text = it!!.status.toString()
//            }


            val retrofit = ServiceBuilder.buildService(RestApi::class.java)
            retrofit.signIn(userRequest).enqueue(
                object : Callback<ResponseApi> {
                    override fun onResponse(
                        call: Call<ResponseApi>,
                        response: Response<ResponseApi>
                    ) {
                        if (response.isSuccessful)
                            Toast.makeText(this@SignInActivity, "Success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                        Toast.makeText(this@SignInActivity, "Fail", Toast.LENGTH_SHORT).show()
                    }

                }
            )


//            val retrofit = ServiceBuilder.buildService(RestApi::class.java)
//            val call: Call<TestApi?>? = retrofit.getCourse()
//            call!!.enqueue( object : Callback<TestApi?> {
//                override fun onResponse(call: Call<TestApi?>, response: Response<TestApi?>) {
//                    if (response.isSuccessful){
////                        Toast.makeText(this@SignInActivity, "Success", Toast.LENGTH_SHORT).show()
//                        val data = response.body()
//
//                        binding.redirectSignUp.text = data!!.data.data.get(0).location.street.name
////                        Toast.makeText(this@SignInActivity, data!!.statusCode, Toast.LENGTH_SHORT).show()
//
//
//                    }
//                }
//
//                override fun onFailure(call: Call<TestApi?>, t: Throwable) {
//                    Toast.makeText(this@SignInActivity, "Fail load data!!!", Toast.LENGTH_SHORT).show()
//                }
//            }
//            )
        }

        binding.emailInput.setOnClickListener {
            binding.email.error = null
        }

        binding.passwordInput.setOnClickListener {
            binding.password.isErrorEnabled = false
            binding.password.error = null
        }


        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private fun validField() {
        if (binding.email.editText?.text.toString().trim().isEmpty()) {
            binding.email.error = "Email not empty!"
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.editText?.text.toString().trim())
                    .matches()
            ) {
                binding.email.error = "Invalid email format"
            }
        }

        if (binding.password.editText?.text.toString().isEmpty()) {
            binding.password.isErrorEnabled = true
            binding.password.error = "Password not empty!"
        } else {
            if (binding.password.editText?.text.toString().length < 8) {
                binding.password.isErrorEnabled = true
                binding.password.error = "Password must have at least 8 characters"
            }
        }
    }


    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    if (task.isSuccessful) {
                        val account: GoogleSignInAccount? = task.result
                        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = auth.currentUser
                                    Toast.makeText(
                                        this,
                                        "Signed in as ${user?.displayName}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
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