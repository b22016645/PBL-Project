package com.example.pbl_project

import android.app.DownloadManager.Query
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_project.databinding.ActivityFeedBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.feed_detail.*
import kotlinx.android.synthetic.main.feed_detail.view.*
import model.Post



class FeedActivity : AppCompatActivity() {
    //private lateinit var firestoreDb : FirebaseStorage
    private lateinit var posts: MutableList<Post>
    private  lateinit var adapter: FeedAdapter
    private lateinit var binding: ActivityFeedBinding
    private val TAG = "FeedActivity"

    private val uid = Firebase.auth.currentUser?.uid
    val postID = "1bjTSGaOIk3IcgNzfB9Y"

    val db: FirebaseFirestore = Firebase.firestore
    val usersCollectionRef = db.collection("users")
    val IDDocumentRef = usersCollectionRef.document(uid!!)
    val postsCollectionRef = IDDocumentRef.collection("posts")
    val postIDDocumentRef = postsCollectionRef.document(postID)
    val commentCollectionRef = postIDDocumentRef.collection("comments")
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)

        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)





        posts = mutableListOf()
        adapter = FeedAdapter(this, posts)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)


        //firestoreDb = FirebaseStorage.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        val postReference = firestore
            .collection("users").document("xeT4aAkdVoazDB8VJrK7dX5YcrZ2")
            .collection("posts")//.document("")

        postReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post ${post}")
            }

        }




    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(
            R.menu.menu_feed,
            menu
        )       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when (item!!.itemId) {
            android.R.id.home -> { // 메뉴 버튼
                startActivity(
                    Intent(this, MyPageActivity::class.java)
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
