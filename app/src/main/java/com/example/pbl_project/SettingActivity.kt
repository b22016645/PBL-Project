package com.example.pbl_project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.pbl_project.databinding.ActivitySettingBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class SettingActivity : AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivitySettingBinding
    private val uid = Firebase.auth.currentUser?.uid

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document(uid!!)
    private var profileURI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.currentUser ?: finish()
        storage = Firebase.storage

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.settingtoolbar)
        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_done_foreground)

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_back, menu)
            return true
        }

        checksex()

        binding.editimagebtn.setOnClickListener {
            openGallery()
        }
        binding.editmessagebtn.setOnClickListener {
            IDDocumentRef.update("message", binding.editmessage.text.toString())
                .addOnSuccessListener {
                    Snackbar.make(binding.root, "상태메세지 변경 완료", Snackbar.LENGTH_SHORT).show()
                }
        }
        binding.editnamebtn.setOnClickListener {
            IDDocumentRef.update("nick", binding.editname.text.toString())
                .addOnSuccessListener {
                    Snackbar.make(binding.root, "닉네임 변경 완료", Snackbar.LENGTH_SHORT).show()
                }
        }

        binding.logoutbtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this,LoginActivity::class.java))
            finish()
        }

    }
    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                //완료 버튼 눌렀을 때
                addProfileImage()
                //성별 저장
                IDDocumentRef.update("sex", profileURI)
                    .addOnSuccessListener {

                    }
                Snackbar.make(binding.root, "마이페이지로 돌아가기", Snackbar.LENGTH_SHORT).show()
                finish()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,1)
    }

    private fun uploadFile(fileName: String) {
        val imageRef = storage.reference.child("profileimages/${uid!!}/${fileName}") // StorageReference

        imageRef.putFile(profileURI!!).addOnCompleteListener {
            if (it.isSuccessful) {
                Snackbar.make(binding.root, "Upload completed.", Snackbar.LENGTH_SHORT).show()
            }
            else {
                Log.d("로그","uploadfile falied")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1) {
                val imageURL : Uri? = data?.data
                profileURI = imageURL

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,profileURI)
                    binding.profileimage.setImageBitmap(bitmap)

                }catch (e: Exception){

                }

            }
        } else {
            Log.d("로그","gallery error")
        }
    }

    private fun addProfileImage() {
        //id 저장해놓기
        IDDocumentRef.update("profile", profileURI)
            .addOnSuccessListener {
                uploadFile("${profileURI}.png")
            }
    }

    private fun checksex() {
        IDDocumentRef.get()
            .addOnSuccessListener {
                val sex = it["sex"]
                if (sex == true) {
                    binding.sex.check(R.id.male)
                } else {
                    binding.sex.check(R.id.female)
                }
            }
    }
}