package com.example.pbl_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            Intent(this, FindPwActivity::class.java))
        finish()
    }
}