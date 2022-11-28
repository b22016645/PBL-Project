package model

import com.example.pbl_project.R
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.database.PropertyName
import com.google.firebase.firestore.auth.User
import com.google.j2objc.annotations.Property

data class Post (
    var content: String = "null",
    @get:PropertyName("photo") @set:PropertyName("photo") var imageUrl: String = "https://firebasestorage.googleapis.com/v0/b/projectpbl-a3842.appspot.com" +
            "/o/photoimages%2FmRIWK4AeJjC3EUWl00at%2F1669269637994.png?alt=media&token=377ebac0-21e4-4c97-816f-97cc10004ac5\"",
//    @get:PropertyName("photo") @set:PropertyName("photo") var imageUrl: String = ",
    @get:PropertyName("like") @set:PropertyName("like") var like: String = "0",
    var id : String = "null"
)