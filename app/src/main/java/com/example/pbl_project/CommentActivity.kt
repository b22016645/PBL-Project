package com.example.pbl_project

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_project.databinding.ActivityCommentBinding
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

        binding.backbtn.setOnClickListener {
            //이전으로 돌아가기
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