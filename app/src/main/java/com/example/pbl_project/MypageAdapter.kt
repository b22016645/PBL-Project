package com.example.pbl_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feed_detail.view.*
import kotlinx.android.synthetic.main.mypage_detail.view.*
import model.Post

class MypageAdapter (val context: Context, val posts : List<Post>) :
    RecyclerView.Adapter<MypageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mypage_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            itemView.mypagecontent.text = post.content
            Glide.with(context).load(post.imageUrl).into(itemView.mypageimage)
            itemView.like_number.text = post.like
        }
    }
}