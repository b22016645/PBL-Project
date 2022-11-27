package com.example.pbl_project

import android.app.DownloadManager.Query
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_project.databinding.ActivityFeedBinding
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_feed.*
import model.Post


private const val TAG = "FeedActivity"
class FeedActivity : AppCompatActivity() {
    private lateinit var firestoreDb : FirebaseStorage
    private lateinit var posts: MutableList<Post>
    private  lateinit var adapter: FeedAdapter

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

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(
                R.menu.menu_feed,
                menu
            )       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
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

//            fun onOptionsItemSelected(item: MenuItem?): Boolean {
//                // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
//                when (item!!.itemId) {
//                    android.R.id.home -> { // 메뉴 버튼
//                        //Snackbar.make(toolbar1,"Menu pressed",Snackbar.LENGTH_SHORT).show()
//                    }
//                }
//                return super.onOptionsItemSelected(item)
//            }


        posts = mutableListOf()
        adapter = FeedAdapter(this, posts)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        val firestore = FirebaseFirestore.getInstance()
        val postReference = firestore
            .collection("posts")
            .limit(20)
            .addSnapshotListener {snapshot, exception ->
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




}
