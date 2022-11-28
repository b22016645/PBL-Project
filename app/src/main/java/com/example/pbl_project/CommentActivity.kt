package com.example.pbl_project

import android.graphics.Bitmap
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_project.databinding.ActivityCommentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_comment.*

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

    lateinit var commentAdapter: CommentAdapter
    val datas = mutableListOf<CommentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        commentview.layoutManager = layoutManager

        commentAdapter = CommentAdapter(this)
        commentview.adapter = commentAdapter

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
            var comment = binding.comment.text.toString()
            IDDocumentRef.get()
                .addOnSuccessListener {
                    val commentMap = hashMapOf(
                        "comment" to comment,
                        "id" to uid!!
                    )
                    addComment(commentMap)
                }
        }
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                //뒤로가기 버튼 눌렀을 때
                Snackbar.make(binding.root, "메인페이지로 돌아가기", Snackbar.LENGTH_SHORT).show()
                finish()
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
                        val imageRef = storage.reference.child("profileimages/${id}/${it["profile"].toString()}.png")
                        displayImageRef(imageRef, binding.profileimage)
                    }.addOnFailureListener {
                        Log.d("로그","게시글 가져오기 실패")
                    }
            }.addOnFailureListener {
                Log.d("로그","게시글 가져오기 실패")
            }
    }
    private fun addComment(commentMap: HashMap<String,String>){
        commentCollectionRef.add(commentMap)
            .addOnSuccessListener {
                createCommentView(it.id)
            }.addOnFailureListener {
                Snackbar.make(binding.root, "댓글 띄우기 실패", Snackbar.LENGTH_SHORT).show()
            }
    }
    private fun settingView() {
        commentAdapter = CommentAdapter(this)
        commentview.adapter = commentAdapter

        commentCollectionRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var commentID = document.id
                    createCommentView(commentID)
                }
            }.addOnFailureListener {

            }
    }

    private fun createCommentView(commentID : String) {
        commentAdapter = CommentAdapter(this)
        commentview.adapter = commentAdapter

        datas.apply {
            commentCollectionRef.document(commentID).get()
                .addOnSuccessListener {
                    var content = it["comment"].toString()
                    var id = it["id"].toString()
                    usersCollectionRef.document(id).get()
                        .addOnSuccessListener {
                            val nick = it["nickname"].toString()
                            val imageRef = storage.reference.child("profileimages/${id}/${it["profile"].toString()}.png")
                            imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
                                var bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                                add(CommentData(img = bmp, nick = nick, content = content ))
                            }
                        }
                }

            commentAdapter.datas = datas
            commentAdapter.notifyDataSetChanged()
        }
    }

    private fun refreshLike() {
        postIDDocumentRef.get()
            .addOnSuccessListener {
                binding.likenum.setText(it["like"].toString())
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