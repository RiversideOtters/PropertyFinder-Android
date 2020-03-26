package com.chalkbox.propertyfinder.dto.pojos

import com.chalkbox.propertyfinder.dto.pojos.base.Pojo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference


class User(
    var name: String? = "",
    var phone: String? = "",
    var gender: PropertySettingValue? = PropertySettingValue(),
    var email: String? = "",
    var images: MutableList<String> = mutableListOf(),
    var firebaseUser: FirebaseUser? = null
) : Pojo {
    companion object {
        val NULL_USER = User(null, null, null, null, mutableListOf(), null)
    }
}