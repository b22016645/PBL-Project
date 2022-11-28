package com.example.pbl_project

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
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
        fun bind(post: Post) {
            itemView.nickname_feed.text = post.id
            itemView.content_feed.text = post.content
            Glide.with(itemView).load(post.imageUrl).placeholder(R.mipmap.ic_launcher).into(itemView.feedimage)
            itemView.like_number_feed.text = post.like


        }

    }


}


