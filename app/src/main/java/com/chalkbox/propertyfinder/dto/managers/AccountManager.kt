package com.chalkbox.propertyfinder.dto.managers

import com.chalkbox.propertyfinder.dto.pojos.User
import io.reactivex.Observable

data class ApiResult(val success: Boolean, val message: String)

interface AccountManager {
    fun getCurrentUser(): User
    fun signUp(name: String, email: String, password: String): Observable<ApiResult>
    fun signIn(email: String = "", password: String = ""): Observable<ApiResult>
    fun signOut()
}