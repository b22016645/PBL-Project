package com.example.pbl_project

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.donebtn.setOnClickListener {
            //마이페이지로 돌아가기
        }



    }
}