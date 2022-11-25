package com.example.pbl_project

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.pbl_project.databinding.ActivityCommentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class CommentActivity : AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityCommentBinding
    private val uid = Firebase.auth.currentUser?.uid
    val postID = "1bjTSGaOIk3IcgNzfB9Y" //postID 넘어올때같이받기

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document(uid!!)
    val postsCollectionRef = IDDocumentRef.collection("posts")
    val postIDDocumentRef = postsCollectionRef.document(postID)
    val commentCollectionRef = postIDDocumentRef.collection("comments")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.currentUser ?: finish()
        storage = Firebase.storage

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.commenttoolbar)
        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_whiteback_foreground)

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_back, menu)
            return true
        }
        getContent()
        settingView()
        refreshLike()

        binding.like.setOnClickListener {
            postIDDocumentRef.get()
                .addOnSuccessListener {
                    var like = it["like"].toString()
                    var likenum = like.toInt()
                    likenum ++
                    like = likenum.toString()

                    postIDDocumentRef.update("like", like)
                        .addOnSuccessListener { refreshLike() }
                }
        }

        binding.addcommentbtn.setOnClickListener {
            //등록
            val comment = binding.comment.text.toString()
            val commentMap = hashMapOf(
                "comment" to comment,
                "id" to uid!!
            )
            addComment(commentMap)
        }
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                //뒤로가기 버튼 눌렀을 때
                Snackbar.make(binding.root, "메인페이지로 돌아가기", Snackbar.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun getContent() {
        postIDDocumentRef.get()
            .addOnSuccessListener {
                binding.content.setText(it["content"].toString())
                val id = it["id"].toString()
                usersCollectionRef.document(id).get()
                    .addOnSuccessListener {
                        binding.nickname.setText(it["nickname"].toString())

                        val imageRef = storage.reference.child("photoimages/${postID}")
                        displayImageRef(imageRef, binding.profileimage)
                    }.addOnFailureListener {
                        Log.d("로그","게시글 가져오기 실패")
                    }
            }.addOnFailureListener {
                Log.d("로그","게시글 가져오기 실패")
            }
    }
    private fun addComment(commentMap: HashMap<String,String>){
        commentCollectionRef
            .add(commentMap)
            .addOnSuccessListener {
                createCommentView(it.id)
            }.addOnFailureListener {

            }
    }
    private fun settingView() {
        commentCollectionRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    createCommentView(document.id)
                }
            }
    }

    private fun refreshLike() {
        postIDDocumentRef.get()
            .addOnSuccessListener {
                binding.likenum.setText(it["like"].toString())
            }
    }

    private fun createCommentView(commentID : String) {
        commentCollectionRef.document(commentID).get()
            .addOnSuccessListener {
                val textViewNm : TextView = TextView(applicationContext)
                textViewNm.setText(it["content"].toString())
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