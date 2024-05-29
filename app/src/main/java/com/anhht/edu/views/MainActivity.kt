package com.anhht.edu.views

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.anhht.edu.R
import com.anhht.edu.config.SessionManager
import com.anhht.edu.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sessionManager = SessionManager(this)

//        mAuth = FirebaseAuth.getInstance()
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        val auth = Firebase.auth
//        val user = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.fetchStateLogin() == "true") {
                intent = Intent(this@MainActivity, HomeActivity::class.java)
                val option = ActivityOptions.makeCustomAnimation(this, R.anim.right_in, R.anim.left_out).toBundle()
                startActivity(intent, option)
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish()
            } else {
                intent = Intent(this@MainActivity, SignInActivity::class.java)
                val option = ActivityOptions.makeCustomAnimation(this, R.anim.right_in, R.anim.left_out).toBundle()
                startActivity(intent, option)
                finish()
            }
        }, 3000)


//        binding.logoutButton.setOnClickListener {
//            mAuth.signOut()
//            sessionManager.saveStateLogin("false")
//
//            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
//                // Optional: Update UI or show a message to the user
//                val intent = Intent(this@MainActivity, SignInActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
    }
}