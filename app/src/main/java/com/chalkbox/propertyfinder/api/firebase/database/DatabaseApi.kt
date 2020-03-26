package com.chalkbox.propertyfinder.api.firebase.database

import com.google.firebase.firestore.DocumentReference
import io.reactivex.Observable

interface DatabaseApi<T> {
    val observable: Observable<List<T>>
    fun get()
}