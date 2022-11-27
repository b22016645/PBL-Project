package model

import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.firebase.database.PropertyName
import com.google.firebase.firestore.auth.User
import com.google.j2objc.annotations.Property

data class Post (
    var description: String = "",
    @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl: String = "",
    var user : User? = null
)