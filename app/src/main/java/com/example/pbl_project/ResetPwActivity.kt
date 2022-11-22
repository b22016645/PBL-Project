package com.example.pbl_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityResetpwBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class ResetPwActivity: AppCompatActivity() {
    private val firebaseStore = FirebaseFirestore.getInstance()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityResetpwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.findIdEmail
        database = FirebaseDatabase.getInstance().reference


        binding.findIdSubmit.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"재설정 메일을 보냈습니다",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"이메일을 확인하세요",Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this,LoginActivity::class.java))
                }
        }
    }
}
//Log.w("로그",findEmail)
//Log.w("로그",i.child("email").toString())
//Log.w("로그",findName)
//Log.w("로그",i.child("nickname").toString())