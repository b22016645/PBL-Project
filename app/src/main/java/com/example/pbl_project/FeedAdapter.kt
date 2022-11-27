package com.example.pbl_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        fun bind(post: Post) {
            itemView.nickname_feed.text = post.user.toString()
            itemView.content_feed.text = post.description
            Glide.with(context).load(post.imageUrl).into(itemView.feedimage)
        }
    }
}

























//
//class FeedAdapter(private val itemList: ArrayList<item>) : RecyclerView.Adapter<FeedAdapter.CustomViewHolder>() {
//    inner class CustomViewHolder(itemView: View) : RecyclerView.viewHolder(itemView){
//
//
//
//
//
//
//
//
//    }
//
//
//    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        var view = LayoutInflater.from(inflater.context).inflate(R.layout.activity_feed,container,false)
//
//
//        view.recyclerview.adapter = RecyclerViewAdapter()
//        view.recyclerview.layoutManager = LinearLayoutManager(inflater.context)
//        return view
//    }
//
//
//
//
//    class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//
//        var contentDTOs : ArrayList<ContentDTO>
//        var contentUidList : ArrayList<String>
//
//        //로그인된 유저의 uid
//        var uid = FirebaseAuth.getInstance().currentUser?.uid
//
//        init {
//
//            contentDTOs = ArrayList()
//            contentUidList = ArrayList()
//
//
//            Firebase.firestore?.collection("users")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                contentDTOs!!.clear()
//                contentUidList!!.clear()
//                for(snapshot in querySnapshot!!.documents) {
//
//                    var item = snapshot.toObject(ContentDTO::class.java)
//                    if (item != null) {
//                        contentDTOs!!.add(item)
//                    }
//                    contentUidList!!.add(snapshot.id)
//
//
//
//                        }
//                notifyDataSetChanged()
//
//
//                }
//
//
//
//        }
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//            var view = LayoutInflater.from(parent.context).inflate(R.layout.feed_detail, parent, false)
//
//            return CustomViewHolder(view)
//        }
//        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
//
//        }
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            val viewHolder = (holder as CustomViewHolder).itemView
//            //유저 아이디
//            viewHolder.nickname_feed.text = contentDTOs!![position].userId
//
//            //이미지
//            //Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl).into(viewHolder.feedimage)
//            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl).apply(
//                RequestOptions()
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//            ).into(viewHolder.feedimage)
//
////            val rootRef = Firebase.storage.reference
////
////            val ref = rootRef.child("users")
////            ref.getBytes(Long.MAX_VALUE).addOnCompleteListener {
////                if (it.isSuccessful) {
////                    val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size)
////                    val imgView = findViewById<ImageView>(R.id.feedimage)
////                    imgView.setImageBitmap(bmp)
////                }
////            }
////            var loadingImages = FirebaseStorage.getInstance().reference.child("users").child(contentDTOs!![position].imageFilename.toString())
////            loadingImages.downloadUrl.addOnSuccessListener { uri -> Glide.with(holder.itemView.context).load(uri.toString()).into(viewHolder.feedimage) }
//            //게시글
//            viewHolder.content_feed.text = contentDTOs!![position].explain
//
//            //좋아요
////            binding.recyclerview.like_feed.setOnClickListener {
////                postIDDocumentRef.get()
////                    .addOnSuccessListener {
////                        var like = it["like"].toString()
////                        var likenum = like.toInt()
////                        likenum ++
////                        like = likenum.toString()
////
////                        postIDDocumentRef.update("like", like)
////                            .addOnSuccessListener { refreshLike() }
////                    }
////            }
//          viewHolder.like_number_feed.text = contentDTOs!![position].favoriteCount.toString()
//            //viewholder.like_number_feed.text = "Likes " + contentDTOs!![p1].favoriteCount
//
//        }
//
//        override fun getItemCount(): Int {
//            return contentDTOs!!.size
//        }