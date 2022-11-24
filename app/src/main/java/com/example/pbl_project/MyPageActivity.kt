package com.example.pbl_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityMypageBinding

class MyPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}