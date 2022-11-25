package com.example.pbl_project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.pbl_project.databinding.ActivityPostingBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class PostingActivity : AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityPostingBinding
    private val uid = Firebase.auth.currentUser?.uid

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document(uid!!)
    val postsCollectionRef = IDDocumentRef.collection("posts")
    private var photoURI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.postingtoolbar)
        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_whiteback_foreground)

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_back, menu)
            return true
        }

        Firebase.auth.currentUser ?: finish() // if not authenticated, finish this activity
        storage = Firebase.storage

        binding.postingbtn.setOnClickListener{
            val content = binding.postcontent.text.toString()
            val postMap = hashMapOf(
                "content" to content,
                "like" to "0",
                "photo" to photoURI.toString(),
                "id" to uid!!
            )
            addPost(postMap)
        }

        binding.photobtn.setOnClickListener {
            openGallery()
        }

    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                //뒤로가기 버튼 눌렀을 때
                Snackbar.make(binding.root, "마이페이지로 돌아가기", Snackbar.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addPost(postMap: HashMap<String,String>) {
        postsCollectionRef.add(postMap)
            .addOnSuccessListener {
                uploadFile("${System.currentTimeMillis()}.png",it.id)
                Log.d("로그","postingsuccess")
            }.addOnFailureListener {
                Log.d("로그","postingfailed")
            }
        //마이페이지돌아가기
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,1)
    }

    private fun uploadFile(fileName: String, postID: String) {
        val imageRef = storage.reference.child("photoimages/${postID}/${fileName}") // StorageReference

        imageRef.putFile(photoURI!!).addOnCompleteListener {
            if (it.isSuccessful) {
                Snackbar.make(binding.root, "Upload completed.", Snackbar.LENGTH_SHORT).show()
            }
            else {
                Log.d("로그","uploadfile falied ㅠㅠ")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1) {
                val imageURL : Uri? = data?.data
                photoURI = imageURL

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageURL)
                    binding.photo.setImageBitmap(bitmap)

                }catch (e:Exception){

                }

            }
        } else {
            Log.d("로그","에러에러")
        }
    }

}