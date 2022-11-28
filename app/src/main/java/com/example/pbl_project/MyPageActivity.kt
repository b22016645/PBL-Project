package com.example.pbl_project

import android.app.DownloadManager.Query
import android.content.Intent
import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets.add
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_project.databinding.ActivityMypageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.feed_detail.*
import kotlinx.android.synthetic.main.mypage_detail.*
import model.Post



class MyPageActivity : AppCompatActivity() {
    private lateinit var firestoreDb : FirebaseStorage
    private lateinit var posts: MutableList<Post>
    private  lateinit var adapter: MypageAdapter
    private val TAG = "MypageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)

        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)







        posts = mutableListOf()
        adapter = MypageAdapter(this, posts)
        re.adapter = adapter
        re.layoutManager = LinearLayoutManager(this)

        val firestore = FirebaseFirestore.getInstance()
        val postReference = firestore
            .collection("users").document("IapvxRJRHvdyqPZujkFARxfsDYI3").collection("posts")
            .limit(20)
        postReference.addSnapshotListener {snapshot, exception ->
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
            R.menu.menu_mypage,
            menu
        )       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(this, FeedActivity::class.java)
                )
            }
            R.id.menu_add -> {
                startActivity(
                    Intent(this, PostingActivity::class.java)
                )
            }
            R.id.menu_setttings -> {
                startActivity(
                    Intent(this, SettingActivity::class.java)
                )
            }

        }
        return super.onContextItemSelected(item)
    }



}
