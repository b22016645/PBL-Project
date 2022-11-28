package com.example.pbl_project

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feed_detail.view.*
import model.Post


class FeedAdapter  (val context: Context, val posts : List<Post>) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.feed_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])

    }

    override fun getItemCount() = posts.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById<Button>(R.id.comment_feed)
        private val like: Button = itemView.findViewById<Button>(R.id.like_feed)

        fun bind(post: Post) {
            itemView.nickname_feed.text = post.id
            itemView.content_feed.text = post.content
            Glide.with(itemView).load(post.imageUrl).into(itemView.feedimage)
            itemView.like_number_feed.text = post.like


            button.setOnClickListener {
                Intent(context, CommentActivity::class.java).apply {
                   // putExtra("data", posts)
                    //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }

            like.setOnClickListener {
                var likenum = post.like.toInt()
                likenum ++
                post.like = likenum.toString()

            }

        }

    }
}


