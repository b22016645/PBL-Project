package com.example.pbl_project

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityFindpwBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class FindPwActivity: AppCompatActivity() {
    private lateinit var firebaseStore: FirebaseFirestore
    private val database by lazy { FirebaseDatabase.getInstance() }
    private val userRef = database.getReference("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFindpwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseStore = FirebaseFirestore.getInstance()

//        val findName = binding.findIdName.text.toString()
//        val findEmail = binding.findIdEmail.text.toString()

        binding.findIdSubmit.setOnClickListener {
            //1. 유효성 검사
            //2. 본래 ID찾기, PWD찾기 버튼이 들어가려던 곳에 return 값 textView로 넣기

            Log.d("로그", userRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    TODO("Not yet implemented")
                    for (snapshot in p0.children){
                        if (snapshot.key.equals("pwd")){
                            binding.returnpwd.text = snapshot.value.toString()
                        }
                    }
                }
            }).toString())
            Log.d("로그", firebaseStore.collection("users").get().toString())
        }
    }
}