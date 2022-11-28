package com.example.pbl_project

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.pbl_project.databinding.ActivityMypageBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MyPageActivity : AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityMypageBinding
    private val uid = Firebase.auth.currentUser?.uid

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document(uid!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.currentUser ?: finish()
        storage = Firebase.storage

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_mypage, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
            return true
        }

        fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            super.onCreateContextMenu(menu, v, menuInfo)

        }

        fun onContextItemSelected(item: MenuItem): Boolean {
            return super.onContextItemSelected(item)
        }

        fun onOptionsItemSelected(item: MenuItem?): Boolean {
            // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
            when(item!!.itemId){
                android.R.id.home->{ // 메뉴 버튼
                    //Snackbar.make(toolbar1,"Menu pressed",Snackbar.LENGTH_SHORT).show()
                }
                R.id.menu_add->{ // 검색 버튼
                    // Snackbar.make(toolbar,"Search menu pressed",Snackbar.LENGTH_SHORT).show()
                }
                R.id.menu_setttings->{ // 계정 버튼
                    // Snackbar.make(toolbar,"Account menu pressed",Snackbar.LENGTH_SHORT).show()
                }

            }
            return super.onOptionsItemSelected(item)
        }

        showProfile()

        binding.dofollowbtn.setOnClickListener {
            //false -> true          true -> false
        }

    }

    private fun showProfile() {
        IDDocumentRef.get()
            .addOnSuccessListener {
                binding.name.setText(it["nickname"].toString())
                val imageRef = storage.reference.child("profileimages/${uid!!}/${it["profile"].toString()}.png")
                displayImageRef(imageRef, binding.myprofile)
                binding.followerNumber.setText(it["follower"].toString())
                binding.followerNumber.setText(it["following"].toString())
            }.addOnFailureListener {
                Log.d("로그","게시글 가져오기 실패")
            }
    }
    private fun displayImageRef(imageRef: StorageReference?, view: ImageView) {
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.setImageBitmap(bmp)
        }?.addOnFailureListener {
        }
    }
}