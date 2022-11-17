package com.example.pbl_project

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document("GhlQreOyU85QsAUi2Fpa")
    val postsCollectionRef = IDDocumentRef.collection("posts")
    private var photoURL : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.currentUser ?: finish() // if not authenticated, finish this activity
        storage = Firebase.storage

        binding.postingbtn.setOnClickListener{
            val content = binding.postcontent.text.toString()
            val postMap = hashMapOf(
                "content" to content,
                "like" to "0",
                "photo" to photoURL
            )
            addPost(postMap)
        }

        binding.photobtn.setOnClickListener {
            openGallery()
        }
    }

    private fun addPost(postMap: HashMap<String,String>) {
        postsCollectionRef.add(postMap)
            .addOnSuccessListener {
                //마이페이지로 돌아가기
            }.addOnFailureListener { }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,1)
    }

    private fun uploadFile(file_id: Long, fileName: String) {
        val imageRef = storage.reference.child("photoimages/${fileName}") // StorageReference
        val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            file_id)
        imageRef.putFile(contentUri).addOnCompleteListener {
            if (it.isSuccessful) {
                Snackbar.make(binding.root, "Upload completed.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1) {
                val imageURL : Uri? = data?.data
                photoURL = imageURL.toString()

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