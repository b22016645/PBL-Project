package com.example.pbl_project

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CommentAdapter (private val context: Context) :

    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var datas = mutableListOf<CommentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nickName: TextView = itemView.findViewById(R.id.commentnick)
        private val comment: TextView = itemView.findViewById(R.id.commentcontent)
        private val profileImg: ImageView = itemView.findViewById(R.id.commentprofile)

        fun bind(item: CommentData) {
            nickName.text = item.nick
            comment.text = item.content
//            profileImg.setImageBitmap(item.img)
            Glide.with(itemView).load(item.img).into(profileImg)

        }
    }
}