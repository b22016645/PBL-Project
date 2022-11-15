package com.example.pbl_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.pbl_project.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.SimpleTimeZone

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Firebase.auth.currentUser == null){
            startActivity(
                Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.textUID.text = Firebase.auth.currentUser?.uid ?: "No user"
        binding.buttonSignout.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }

//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            binding.textFCMToken.text = if (it.isSuccessful) it.result else "Token Error!"
//
//            // copy FCM token to clipboard
//            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clip = ClipData.newPlainText("FCM Token", binding.textFCMToken.text)
//            clipboard.setPrimaryClip(clip)
//
//            // write to logcat
//            Log.d(MyFirebaseMessagingService.TAG, "FCM token: ${binding.textFCMToken.text}")
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
//            createNotificationChannel()
//        }
//    }
//
//    private fun requestSinglePermission(permission: String) {
//        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
//            return
//
//        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//            if (it == false) { // permission is not granted!
//                AlertDialog.Builder(this).apply {
//                    setTitle("Warning")
//                    setMessage("notification permission required!")
//                }.show()
//            }
//        }
//
//        if (shouldShowRequestPermissionRationale(permission)) {
//            // you should explain the reason why this app needs the permission.
//            AlertDialog.Builder(this).apply {
//                setTitle("Reason")
//                setMessage("notification permission required!")
//                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
//                setNegativeButton("Deny") { _, _ -> }
//            }.show()
//        } else {
//            // should be called in onCreate()
//            requestPermLauncher.launch(permission)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel() {
//        val channel = NotificationChannel(
//            "firebase-messaging", "firebase-messaging channel",
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//        channel.description = "This is firebase-messaging channel."
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.storage -> startActivity(
//                Intent(this, StorageActivity::class.java))
//            R.id.remote_config -> startActivity(
//                Intent(this, RemoteConfigActivity::class.java))
//            R.id.firestore -> startActivity(
//                Intent(this, FirestoreActivity::class.java))
//            R.id.realtime_db -> startActivity(
//                Intent(this, RealtimeDBActivity::class.java))
//        }
//        return super.onOptionsItemSelected(item)
//    }
    }

}