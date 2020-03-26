package com.chalkbox.propertyfinder.dto.managers

import android.util.Log
import com.chalkbox.propertyfinder.api.application.componentfactory.FirebaseComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class FirebaseAuthAccountManager @Inject constructor() : AccountManager {
    private val subject = BehaviorSubject.create<User>()
    val observable: Observable<User> = subject

    @Inject
    internal lateinit var firebaseAuth: FirebaseAuth

    @Inject
    internal lateinit var firebaseDynamicLinks: FirebaseDynamicLinks

    init {
        FirebaseComponentFactory().newFirebaseAuthComponent().inject(this)

        val user = getCurrentUser()
        if (user != User.NULL_USER) {
            subject.onNext(user)
        }
    }

    override fun getCurrentUser(): User {
        return if (firebaseAuth.currentUser != null) {
            User(firebaseUser = firebaseAuth.currentUser)
        } else {
            User.NULL_USER
        }
    }

    override fun signIn(email: String, password: String): Observable<ApiResult> {
        return Observable.create { subscriber ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(this::class.simpleName, "signInUserWithEmail:success")
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        // TODO: Get name from User : Pojo
                        subject.onNext(User(firebaseUser = user))
                        subscriber.onNext(ApiResult(true, ""))
                    }
                }
                .addOnFailureListener { result ->
                    Log.w(this::class.simpleName, "signInUserWithEmail:failure", result)
                    subscriber.onError(Error(result.localizedMessage))
                }
                .addOnCanceledListener {
                    Log.w(this::class.simpleName, "signInUserWithEmail:cancelled")
                    subscriber.onError(Error("Cancelled"))
                }
        }
    }

    override fun signUp(name: String, email: String, password: String): Observable<ApiResult> {
        return Observable.create { subscriber ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(this::class.simpleName, "signUpUserWithEmail:success")
                    val firebaseUser = firebaseAuth.currentUser
                    if (firebaseUser != null) {
                        val user = User(firebaseUser = firebaseUser)
                        user.name = name
                        subject.onNext(user)
                        subscriber.onNext(ApiResult(true, ""))
                    }
                }
                .addOnFailureListener { result ->
                    Log.w(this::class.simpleName, "signUpUserWithEmail:failure", result)
                    subscriber.onError(Error(result.localizedMessage))
                }
                .addOnCanceledListener {
                    Log.w(this::class.simpleName, "signUpUserWithEmail:cancelled")
                    subscriber.onError(Error("Cancelled"))
                }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

//    private fun sendAuth(email: String, actionCodeSettings: ActionCodeSettings) {
//        firebaseAuth.sendSignInLinkToEmail(email, actionCodeSettings)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(this::class.simpleName, "Email sent.")
//                }
//            }
//    }
//
//    private fun buildActionCodeSettings() {
//        // [START auth_build_action_code_settings]
//        val actionCodeSettings = ActionCodeSettings.newBuilder()
//            // URL you want to redirect back to. The domain (www.example.com) for this
//            // URL must be whitelisted in the Firebase Console.
//            .setUrl("https://www.example.com/finishSignUp?cartId=1234")
//            // This must be true
//            .setHandleCodeInApp(true)
//            .setIOSBundleId("com.example.ios")
//            .setAndroidPackageName(
//                "com.example.android",
//                true, /* installIfNotAvailable */
//                "12" /* minimumVersion */
//            )
//            .build()
//        // [END auth_build_action_code_settings]
//    }

//    private fun verifyAuthEmailLink(emailLink: String) {
//        val intent = intent
//
//        // Confirm the link is radio_button_pressed sign-in with email link.
//        if (firebaseAuth.isSignInWithEmailLink(emailLink)) {
//            // Retrieve this from wherever you stored it
//            val email = "someemail@domain.com"
//
//            // The client SDK will parse the code from the link for you.
//            firebaseAuth.signInWithEmailLink(email, emailLink)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d(this::class.simpleName, "Successfully signed in with email link!")
//                        val result = task.result
//                        // You can access the new user via result.getUser()
//                        // Additional user info profile *not* available via:
//                        // result.getAdditionalUserInfo().getProfile() == null
//                        // You can check if the user is new or existing:
//                        // result.getAdditionalUserInfo().isNewUser()
//                    } else {
//                        Log.e(
//                            this::class.simpleName,
//                            "Error signing in with email link",
//                            task.exception
//                        )
//                    }
//                }
//        }
//    }
//
//    private fun initDynamicLink() {
//        firebaseDynamicLinks
//            .getDynamicLink(intent)
//            .addOnSuccessListener(this) { pendingDynamicLinkData ->
//                // Get deep link from result (may be null if no link is found)
//                var deepLink: Uri? = null
//                if (pendingDynamicLinkData != null) {
//                    deepLink = pendingDynamicLinkData.link
//                }
//
//                // Handle the deep link. For example, open the linked
//                // content, or apply promotional credit to the user's
//                // account.
//                // ...
//
//
//                // [START_EXCLUDE]
//                // Display deep link in the UI
//                if (deepLink != null) {
//                    val deepLinkString = deepLink.toString()
//                    verifyAuthEmailLink(deepLinkString)
//                    Log.d(this::class.simpleName, deepLinkString)
//                } else {
//                    Log.d(this::class.simpleName, "getDynamicLink: no link found")
//                }
//                // [END_EXCLUDE]
//            }
//            .addOnFailureListener(this) { e ->
//                Log.d(this::class.simpleName, "getDynamicLink:onFailure", e)
//            }
//    }
}