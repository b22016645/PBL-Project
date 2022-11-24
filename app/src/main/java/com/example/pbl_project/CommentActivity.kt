package com.example.pbl_project

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.pbl_project.databinding.ActivityCommentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommentBinding
    val postID = "GhlQreOyU85QsAUi2Fpa"

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document(postID)
    val postsCollectionRef = IDDocumentRef.collection("posts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        refreshView()
        refreshLike()

        binding.like.setOnClickListener {
            var like = ""
            postsCollectionRef.document(postID).get()
                .addOnSuccessListener {
                    like = it["like"].toString()
                    var likenum = like.toInt()
                    likenum ++
                    like = likenum.toString()
                }
            postsCollectionRef.document(postID).update("like", like)
                .addOnSuccessListener { refreshLike() }
        }

        binding.addcommentbtn.setOnClickListener {
            //등록
            val comment = binding.comment.text.toString()
            val like = "0"
            val commentMap = hashMapOf(
                "comment" to comment,
                "like" to like,
                "id" to "GhlQreOyU85QsAUi2Fpa"
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
        postsCollectionRef.document(postID).get()
            .addOnSuccessListener {
                binding.content.setText(it["content"].toString())
            }.addOnFailureListener {
                Log.d("로그","게시글 가져오기 실패")
            }
    }

    private fun addComment(commentMap: HashMap<String,String>){
        postsCollectionRef
            .document("MpoE6QpJRz0DBbjsJOBe")
            .collection("comments")
            .add(commentMap)
            .addOnSuccessListener {
                //댓글창 리프레쉬
                refreshView()
            }.addOnFailureListener {

            }
    }

    private fun refreshView() {

    }

    private fun refreshLike() {
        postsCollectionRef.document(postID).get()
            .addOnSuccessListener {
                var like = it["like"].toString()
                var likenum = like.toInt()
                likenum ++
                binding.likenum.setText(likenum)
            }
    }
}