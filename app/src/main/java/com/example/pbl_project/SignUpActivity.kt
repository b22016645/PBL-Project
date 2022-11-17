package com.example.pbl_project

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Sms.Intents
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.postsignup.setOnClickListener {
            val email = binding.email.text.toString()
            val pw = binding.passwd.text.toString()
            val confirmPw = binding.confirmPw.text.toString()
            val name = binding.name.text.toString()
            val sex = binding.sex.text.toString()

            if (email.isNotEmpty() && pw.isNotEmpty() && confirmPw.isNotEmpty() && name.isNotEmpty() && sex.isNotEmpty()){
                if (pw == confirmPw){
                    firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this,LoginActivity::class.java)
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"빈 항목을 채워주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}