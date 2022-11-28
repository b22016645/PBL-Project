package com.example.pbl_project

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val userID = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            doLogin(userID,password)
        }
        binding.signup.setOnClickListener {
            doSignup()
        }
        binding.resetPW.setOnClickListener {
            doResetPw()
        }
    }
    private fun doLogin(userID: String, password: String){
        Firebase.auth.signInWithEmailAndPassword(userID,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    startActivity(
                        Intent(this, MyPageActivity::class.java  /* 로그인 후에 실행할 엑티비티*/))
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
    private fun doResetPw(){
        startActivity(
            Intent(this, ResetPwActivity::class.java))
        finish()
    }

}