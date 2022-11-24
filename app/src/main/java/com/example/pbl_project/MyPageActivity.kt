package com.example.pbl_project
//
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Bundle
//import android.view.ContextMenu
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import androidx.appcompat.app.ActionBar
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.pbl_project.R
//import com.example.pbl_project.databinding.ActivityFeedBinding.inflate
//import com.example.pbl_project.databinding.ActivityFindpwBinding.inflate
//import com.example.pbl_project.databinding.ActivitySettingBinding.inflate
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.ktx.storage
//
//
//class MyPageActivity  : AppCompatActivity() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //val binding2 = MypageBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_mypage)
//
//        //툴바 설정
//        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
//        setSupportActionBar(toolbar)
//        val ac: ActionBar? = supportActionBar
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)
//
////        //firebase에서 이미지 가져오기
////        val rootRef = Firebase.storage.reference
////
////        val ref = rootRef.child("test.JPG")
////        ref.getBytes(Long.MAX_VALUE).addOnCompleteListener {
////            if (it.isSuccessful) {
////                val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size)
////                //val imgView = findViewById<ImageView>(R.id.imageView2)
////                //imgView.setImageBitmap(bmp)
////            }
////        }
////        val viewModel: Mypage_ViewModel by viewModels()
////
////        binding.recyclerView.adapter = MypageAdapter(viewModel)
////        binding.recyclerView.layoutManager = LinearLayoutManager(this)
////        binding.recyclerView.setHasFixedSize(true)
////
////        registerForContextMenu(binding.recyclerView)
//
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        //menuInflater.inflate(R.menu.mypage_menu, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
//        return true
//    }
//
//    override fun onCreateContextMenu(
//        menu: ContextMenu?,
//        v: View?,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//
//    }
//
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        return super.onContextItemSelected(item)
//    }
//
////   override fun onOptionsItemSelected(item: MenuItem?): Boolean {
////       // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
////       when(item!!.itemId){
////           android.R.id.home->{ // 메뉴 버튼
////                Snackbar.make(toolbar1,"Menu pressed",Snackbar.LENGTH_SHORT).show()
////            }
////            R.id.menu_search->{ // 검색 버튼
////                Snackbar.make(toolbar,"Search menu pressed",Snackbar.LENGTH_SHORT).show()
////            }
////            R.id.menu_account->{ // 계정 버튼
////                Snackbar.make(toolbar,"Account menu pressed",Snackbar.LENGTH_SHORT).show()
////            }
////            R.id.menu_logout->{ // 로그아웃 버튼
////                Snackbar.make(toolbar,"Logout menu pressed",Snackbar.LENGTH_SHORT).show()
////            }
////        }
////        return super.onOptionsItemSelected(item)
////    }
//
//
//}
//
//


import android.content.ContentUris
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pbl_project.com.example.pbl_project.model.ContentDTO
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_mypage.view.*
import kotlinx.android.synthetic.main.mypage_detail.view.*
import kotlinx.android.synthetic.main.mypage_recyclerview_detail.view.*
import java.lang.Long.getLong

//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.fragment_user.view.*


class MyPageActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding2 = MypageBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_mypage)

        //툴바 설정
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)
        val ac: ActionBar? = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mypage, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
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

        fun onOptionsItemSelected(item: MenuItem?): Boolean {
       // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
       when(item!!.itemId){
           android.R.id.home->{ // 메뉴 버튼
                //Snackbar.make(toolbar1,"Menu pressed",Snackbar.LENGTH_SHORT).show()
            }
            R.id.menu_add->{ // 검색 버튼
               // Snackbar.make(toolbar,"Search menu pressed",Snackbar.LENGTH_SHORT).show()
            }
            R.id.menu_setttings->{ // 계정 버튼
               // Snackbar.make(toolbar,"Account menu pressed",Snackbar.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

        MypageDetail()
    }


}

class MypageDetail : Fragment(){
    var firestore : FirebaseFirestore? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        firestore = FirebaseFirestore.getInstance()

        var view = LayoutInflater.from(activity).inflate(R.layout.mypage_recyclerview_detail,container,false)

        view.mypage_recyclerview.adapter = MypageRecyclerviewAdapter()
        view.mypage_recyclerview.layoutManager = LinearLayoutManager(activity)

        return view

    }
    inner class MypageRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//        val storageRef = storage?.reference?.child("images")?.child(imageFileName)    //firebase 버전 오류나면 이걸로
//
//        storageRef?.putFile (photoUri!!)?.addOnFailureListener {
//            progress_bar.visibility = View.GONE
//            Toast.makeText(this, getString(R.string.upload_fail),Toast.LENGTH_SHORT).show()
//
//        }?.addOnSuccessListener{taskSnapshot ->
//            // success
//            // downloadUrl을 받아 올수 있음.
//            storageRef.downloadUrl.addOnCompleteListener {
//                    taskSnapshot ->
//
//                progress_bar.visibility = View.GONE
//                Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
//
//                var uri = taskSnapshot.result.toString()
//                //디비에 바인딩 할 위치 생성 및 컬렉션(테이블)에 데이터 집합 생성
//                //시간 생성
//                val contentDTO = ContentDTO()
//                //이미지 주소
//                contentDTO.imageUrl = uri!!.toString()
//                //유저의 UID
//                contentDTO.uid = auth?.currentUser?.uid
//                //게시물의 설명
//                contentDTO.explain = addphoto_edit_explain.text.toString()

        var contentDTOs : ArrayList<ContentDTO>? = null
        var contentUidList : ArrayList<String>? = null

        //로그인된 유저의 uid
        var uid = FirebaseAuth.getInstance().currentUser?.uid

        init {

            contentDTOs = ArrayList()
            contentUidList = ArrayList()
            firestore?.collection("images")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            //Firebase.firestore?.collection("images")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                contentDTOs!!.clear()
                contentUidList!!.clear()
                for(snapshot in querySnapshot!!.documents) {

                        var item = snapshot.toObject(ContentDTO::class.java)
                        if (snapshot.id == uid) {
                            if (item != null) {
                                contentDTOs!!.add(item)
                            }
                            contentUidList!!.add(snapshot.id)
                        }
                        else {

                        }
                }

                notifyDataSetChanged()
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("Not yet implemented")
            val viewHolder = (holder as CustomViewHolder).itemView
            //유저 아이디
            viewHolder.Name.text = contentDTOs!![position].userId
            //이미지
            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl).into(viewHolder.mypageimage)
            //게시글
            viewHolder.mypagecontent.text = contentDTOs!![position].explain
            //좋아요
            viewHolder.like_number.text = contentDTOs!![position].favoriteCount.toString()
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
            return contentDTOs!!.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
            var view = LayoutInflater.from(parent.context).inflate(R.layout.mypage_detail, parent, false)

            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        }
    }
}












        //firebase에서 이미지 가져오기
//        val idIdx = "id"
//        getImage(getLong(idIdx), "image1")






//        Firebase.storage.reference.child("photoimages/GhlQreOyU85QsAUi2Fpa/").child(fileName).downloadUrl
//            .addOnSuccessListener { uri ->
//                mSuccessHandler(uri.toString())
//            }.addOnFailureListener {
//                mErrorHandler()
//            }
//        fragmentView =
//            LayoutInflater.from(parent).inflate(R.layout.activity_mypage, container, false)
//        uid = auth?.uid
//        firestore = FirebaseFirestore.getInstance()
//        auth = FirebaseAuth.getInstance()
//        currentUserUid = auth?.currentUser?.uid
//        var currentUseremail = auth?.currentUser?.email
//
//
//        if (uid == currentUserUid) {
//            MyPage
//            uploadPhoto()
//            fragmentView?.account_btn_follow_signout?.text = getString(R.string.signout)
//            fragmentView?.
//            fragmentView?.account_btn_follow_signout?.setOnClickListener {
//              parent?.finish()
//              startActivity(Intent(parent, LoginActivity::class.java))
//              auth?.signOut()
//            }
//        }

    //}

//    fun getImage(file_id: Long, fileName: String) {
//        val rootRef = Firebase.storage.reference
//
//        val ref = rootRef.child("photoimages/GhlQreOyU85QsAUi2Fpa/${fileName}")
//        //val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,file_id)
//
//        ref.getBytes(Long.MAX_VALUE).addOnCompleteListener {
//            if (it.isSuccessful) {
//                val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result!!.size)
//                val imgView = findViewById<ImageView>(R.id.mypageimage)
//                imgView.setImageBitmap(bmp)
//            }
//            else {
//                return@addOnCompleteListener
//            }
//        }
//    }
//}

































//
//    var fragmentView: View? = null
//    var firestore: FirebaseFirestore? = null
//    var uid: String? = null
//    var auth: FirebaseAuth? = null
//    var currentUserUid: String? = null
//    var currentUseremail : String? = null



//    companion object {
//        var PICK_PROFILE_FROM_ALBUM = 10
//    }
//
//    fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //툴바 설정
//        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
//        setSupportActionBar(toolbar)
//        val ac: ActionBar? = supportActionBar
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_foreground)
//
//
//
//        Firebase.storage.reference.child("/photo").child(fileName).downloadUrl
//            .addOnSuccessListener { uri ->
//                mSuccessHandler(uri.toString())
//            }.addOnFailureListener {
//                mErrorHandler()
//            }
//        fragmentView =
//            LayoutInflater.from(parent).inflate(R.layout.activity_mypage, container, false)
//        uid = auth?.uid
//        firestore = FirebaseFirestore.getInstance()
//        auth = FirebaseAuth.getInstance()
//        currentUserUid = auth?.currentUser?.uid
//        var currentUseremail = auth?.currentUser?.email
//
//
//        if (uid == currentUserUid) {
//            //MyPage
//            //uploadPhoto()
////            fragmentView?.account_btn_follow_signout?.text = getString(R.string.signout)
////            fragmentView?.
////            fragmentView?.account_btn_follow_signout?.setOnClickListener {
////              parent?.finish()
////              startActivity(Intent(parent, LoginActivity::class.java))
////              auth?.signOut()
////            }
//        }
//        else{
//            //OtherUserPage
//            fragmentView?.account_btn_follow_signout?.text = getString(R.string.follow)
//            var mainactivity = (activity as MainActivity)
//            mainactivity?.toolbar_username?.text = arguments?.getString("userId")
//            mainactivity?.toolbar_btn_back?.setOnClickListener {
//                mainactivity.bottom_navigation.selectedItemId = R.id.action_home
//            }
//            mainactivity?.toolbar_title_image?.visibility = View.GONE
//            mainactivity?.toolbar_username?.visibility = View.VISIBLE
//            mainactivity?.toolbar_btn_back?.visibility = View.VISIBLE
//            fragmentView?.account_btn_follow_signout?.setOnClickListener {
//                requestFollow()
//            }
//        }
//        fragmentView?.account_recyclerview?.adapter = UserFragmentRecyclerViewAdapter()
//        fragmentView?.account_recyclerview?.layoutManager = GridLayoutManager(activity!!, 3)
//
//        fragmentView?.account_iv_profile?.setOnClickListener {
//
//        }
//        var photoPickerIntent = Intent(Intent.ACTION_PICK)
//        photoPickerIntent.type = "image/*"
//        parent?.startActivityForResult(photoPickerIntent, PICK_PROFILE_FROM_ALBUM)
        //getProfileImage()


        // getFollowerAndFollowing()
        //return fragmentView
    //}

//    private fun uploadPhoto(
//        imageURI: Uri,
//        mSuccessHandler: (String) -> Unit,
//        mErrorHandler: () -> Unit,
//    ) {
//        val fileName = "${System.currentTimeMillis()}.png"
//        Firebase.storage.reference.child("/photo").child(fileName)
//            .putFile(imageURI)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    // 파일 업로드에 성공했기 때문에 파일을 다시 받아 오도록 해야함
//                    Firebase.storage.reference.child("article/photo").child(fileName).downloadUrl
//                        .addOnSuccessListener { uri ->
//                            mSuccessHandler(uri.toString())
//                        }.addOnFailureListener {
//                            mErrorHandler()
//                        }
//                } else {
//                    mErrorHandler()
//                }
//            }
//    }
//}
//    fun getFollowerAndFollowing(){
//        firestore?.collection("users")?.document(uid!!)?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
//            if(documentSnapshot == null) return@addSnapshotListener
//            var followDTO = documentSnapshot.toObject(FollowDTO::class.java)
//            if(followDTO?.followingCount != null){
//                fragmentView?.account_tv_following_count?.text = followDTO?.followingCount?.toString()
//            }
//            if(followDTO?.followerCount != null){
//                fragmentView?.account_tv_follower_count?.text = followDTO?.followerCount?.toString()
//                if(followDTO?.followers?.containsKey(currentUserUid!!)){
//                    fragmentView?.account_btn_follow_signout?.text = getString(R.string.follow_cancel)
//                    fragmentView?.account_btn_follow_signout?.background
//                        ?.setColorFilter(ContextCompat.getColor(activity!!,R.color.colorLightGray),PorterDuff.Mode.MULTIPLY)
//                }else{
//                    if(uid != currentUserUid){
//                        fragmentView?.account_btn_follow_signout?.text = getString(R.string.follow)
//                        fragmentView?.account_btn_follow_signout?.background?.colorFilter = null
//                    }
//
//                }
//            }
//        }
//    }
//    fun requestFollow(){
//        //Save data to my account
//        var tsDocFollowing = firestore?.collection("users")?.document(currentUserUid!!)
//        firestore?.runTransaction { transaction ->
//            var followDTO = transaction.get(tsDocFollowing!!).toObject(FollowDTO::class.java)
//            if(followDTO == null){
//                followDTO = FollowDTO()
//                followDTO!!.followingCount = 1
//                followDTO!!.followings[uid!!] = true
//
//                transaction.set(tsDocFollowing,followDTO!!)
//                return@runTransaction
//            }
//
//            if(followDTO.followings.containsKey(uid)){
//                //It remove following third person when a third person follow me
//                followDTO?.followingCount = followDTO?.followingCount - 1
//                followDTO?.followings.remove(uid)
//            }else{
//                //It add following third person when a third person do not follow me
//                followDTO?.followingCount = followDTO?.followingCount + 1
//                followDTO?.followings[uid!!] = true
//            }
//            transaction.set(tsDocFollowing,followDTO)
//            return@runTransaction
//        }
//
//        //Save data to third account
//
//        var tsDocFollower = firestore?.collection("users")?.document(uid!!)
//        firestore?.runTransaction { transaction ->
//            var followDTO = transaction.get(tsDocFollower!!).toObject(FollowDTO::class.java)
//            if(followDTO == null){
//                followDTO = FollowDTO()
//                followDTO!!.followerCount = 1
//                followDTO!!.followers[currentUserUid!!] = true
//                followerAlarm(uid!!)
//                transaction.set(tsDocFollower,followDTO!!)
//                return@runTransaction
//            }
//
//            if(followDTO!!.followers.containsKey(currentUserUid!!)){
//                //It cancel my follower when I follow a third person
//                followDTO!!.followerCount = followDTO!!.followerCount - 1
//                followDTO!!.followers.remove(currentUserUid!!)
//            }else{
//                //It add my follower when I don't follow a third person
//                followDTO!!.followerCount = followDTO!!.followerCount + 1
//                followDTO!!.followers[currentUserUid!!] = true
//                followerAlarm(uid!!)
//            }
//            transaction.set(tsDocFollower,followDTO!!)
//            return@runTransaction
//        }
//    }
//    fun followerAlarm(destinationUid : String){
//        var alarmDTO = AlarmDTO()
//        alarmDTO.destinationUid = destinationUid
//        alarmDTO.userId = auth?.currentUser?.email
//        alarmDTO.uid = auth?.currentUser?.uid
//        alarmDTO.kind = 2
//        alarmDTO.timestamp = System.currentTimeMillis()
//        FirebaseFirestore.getInstance().collection("alarms").document().set(alarmDTO)
//
//        var message = auth?.currentUser?.email + getString(R.string.alarm_follow)
//        FcmPush.instance.sendMessage(destinationUid,"Howlstagram",message)
//    }
//    fun getProfileImage(){
//        firestore?.collection("profileImages")?.document(uid!!)?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
//            if(documentSnapshot == null) return@addSnapshotListener
//            if(documentSnapshot.data != null){
//                var url = documentSnapshot?.data!!["image"]
//                Glide.with(activity!!).load(url).apply(RequestOptions().circleCrop()).into(fragmentView?.account_iv_profile!!)
//            }
//        }
//    }
//    inner class UserFragmentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
//        var contentDTOs : ArrayList<ContentDTO> = arrayListOf()
//        init {
//            firestore?.collection("images")?.whereEqualTo("uid",uid)?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                //Sometimes, This code return null of querySnapshot when it signout
//                if(querySnapshot == null) return@addSnapshotListener
//
//                //Get data
//                for(snapshot in querySnapshot.documents){
//                    contentDTOs.add(snapshot.toObject(ContentDTO::class.java)!!)
//                }
//                fragmentView?.account_tv_post_count?.text = contentDTOs.size.toString()
//                notifyDataSetChanged()
//            }
//        }
//
//        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
//            var width = resources.displayMetrics.widthPixels / 3
//
//            var imageview = ImageView(p0.context)
//            imageview.layoutParams = LinearLayoutCompat.LayoutParams(width,width)
//            return CustomViewHolder(imageview)
//        }
//
//        inner class CustomViewHolder(var imageview: ImageView) : RecyclerView.ViewHolder(imageview) {
//
//        }
//
//        override fun getItemCount(): Int {
//            return contentDTOs.size
//        }
//
//        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
//            var imageview = (p0 as CustomViewHolder).imageview
//            Glide.with(p0.itemView.context).load(contentDTOs[p1].imageUrl).apply(RequestOptions().centerCrop()).into(imageview)
//        }
//
//    }
//}
