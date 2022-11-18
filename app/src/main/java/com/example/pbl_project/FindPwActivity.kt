package com.example.pbl_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityFindpwBinding

class FindPwActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFindpwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val findName = binding.findIdName.text.toString()
        val findEmail = binding.findIdEmail.text.toString()

        binding.findIdSubmit.setOnClickListener {
            //1. 유효성 검사
            //2. 본래 ID찾기, PWD찾기 버튼이 들어가려던 곳에 return 값 textView로 넣기
        }
    }
}