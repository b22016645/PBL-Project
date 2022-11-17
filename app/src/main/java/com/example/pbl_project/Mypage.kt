package com.example.pbl_project

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class Mypage  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)

        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)

        //firebase에서 이미지 가져오기
        val rootRef = Firebase.storage.reference

        val ref = rootRef.child("test.JPG")
        ref.getBytes(Long.MAX_VALUE).addOnCompleteListener {
            if (it.isSuccessful) {
                val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size)
                //val imgView = findViewById<ImageView>(R.id.imageView2)
                //imgView.setImageBitmap(bmp)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.mypage_menu, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
//        when(item!!.itemId){
//            android.R.id.home->{ // 메뉴 버튼
//                Snackbar.make(toolbar1,"Menu pressed",Snackbar.LENGTH_SHORT).show()
//            }
//            R.id.menu_search->{ // 검색 버튼
//                Snackbar.make(toolbar,"Search menu pressed",Snackbar.LENGTH_SHORT).show()
//            }
//            R.id.menu_account->{ // 계정 버튼
//                Snackbar.make(toolbar,"Account menu pressed",Snackbar.LENGTH_SHORT).show()
//            }
//            R.id.menu_logout->{ // 로그아웃 버튼
//                Snackbar.make(toolbar,"Logout menu pressed",Snackbar.LENGTH_SHORT).show()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}