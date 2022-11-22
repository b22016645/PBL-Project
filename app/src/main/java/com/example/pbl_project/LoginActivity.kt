package com.example.pbl_project

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var mGoogleSignInClient: GoogleSignInClient?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()

        var googleLoginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == -1) {
                val data = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                getGoogleInfo(task)
            }
        }
        fun googleLogin() {
            val signInIntent = mGoogleSignInClient!!.signInIntent
            googleLoginLauncher.launch(signInIntent)
        }

        binding.LoginWithGoogle.setOnClickListener {
            googleLogin()
        }

        binding.login.setOnClickListener {
            val userID = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            doLogin(userID,password)
        }
        binding.signup.setOnClickListener {
            doSignup()
        }
        binding.findPW.setOnClickListener {
            doFindPw()
        }
    }
    private fun doLogin(userID: String, password: String){
        Firebase.auth.signInWithEmailAndPassword(userID,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    startActivity(
                        Intent(this, MainActivity::class.java  /* 로그인 후에 실행할 엑티비티*/))
                    finish()
                }else{
                    Log.w("LoginActivity", "signWithEmail", it.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun doSignup(){
        startActivity(
            Intent(this, SignUpActivity::class.java))
        finish()
    }
    private fun doFindPw(){
        startActivity(
            Intent(this, ResetPwActivity::class.java))
        finish()
    }
    fun getGoogleInfo(completedTask: Task<GoogleSignInAccount>) {
        try {
            val TAG = "구글 로그인 결과"
            val account = completedTask.getResult(ApiException::class.java)
            Log.d(TAG, account.id!!)
            Log.d(TAG, account.familyName!!)
            Log.d(TAG, account.givenName!!)
            Log.d(TAG, account.email!!)
        }
        catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}