package com.example.pbl_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var firebaseStore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()
        binding.postsignup.setOnClickListener {
            var info = userInfo()

            val email = binding.email.text.toString().trim()
            val pw = binding.pw.text.toString()
            val confirmPw = binding.confirmPw.text.toString()
            val name = binding.name.text.toString()

            binding.sex.setOnCheckedChangeListener{group,checkID->
                when(checkID){
                    R.id.male -> info.sex = true
                    R.id.female -> info.sex = false
                }
            }

            info.uid = firebaseAuth.uid
            info.email = email
            info.nickname = name
            info.message = ""

            if (email.isNotEmpty() && pw.isNotEmpty() && confirmPw.isNotEmpty() && name.isNotEmpty()){
                if(!email.contains("@") && email.length<6){
                    var toast = Toast.makeText(this,"이메일 형식이 맞지 않습니다.",Toast.LENGTH_SHORT).show()
                    Log.d("로그",toast.toString())
                }
                else if (pw == confirmPw){
                    if (name.matches("^[a-zA-Z0-9]*$".toRegex())){
                        firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener {
                            if(it.isSuccessful){
                                firebaseStore?.collection("users")?.document(firebaseAuth.uid.toString())?.set(info)
                                startActivity(Intent(this,LoginActivity::class.java))
                            }else{
                                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                                Log.d("로그",it.exception.toString())
                            }
                        }
                    }else{
                        Toast.makeText(this,"이름은 영문과 숫자만 입력 가능합니다.",Toast.LENGTH_SHORT).show()
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