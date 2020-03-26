package com.chalkbox.propertyfinder.dto.managers

import com.chalkbox.propertyfinder.dto.pojos.User
import io.reactivex.Observable

class UserAccountManager constructor(private val firebaseAuthManager: FirebaseAuthAccountManager) :
    AccountManager {
    var observable: Observable<User> = firebaseAuthManager.observable

    override fun getCurrentUser(): User = firebaseAuthManager.getCurrentUser()

    override fun signUp(name: String, email: String, password: String): Observable<ApiResult> =
        firebaseAuthManager.signUp(name, email, password)

    override fun signIn(email: String, password: String): Observable<ApiResult> =
        firebaseAuthManager.signIn(email, password)

    override fun signOut() = firebaseAuthManager.signOut()
}