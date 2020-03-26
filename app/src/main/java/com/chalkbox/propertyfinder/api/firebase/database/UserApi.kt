package com.chalkbox.propertyfinder.api.firebase.database

import com.chalkbox.propertyfinder.api.application.componentfactory.FirebaseComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UserApi : DatabaseApi<User> {
    @Inject
    internal lateinit var firebaseFirestore: FirebaseFirestore

    private var collectionReference: CollectionReference

    private val subject = BehaviorSubject.create<List<User>>()
    override val observable: Observable<List<User>> = subject

    init {
        FirebaseComponentFactory().newFirebaseDatabaseComponent().inject(this)

        collectionReference = firebaseFirestore.collection(COLLECTION_NAME)

        get()
    }

    override fun get() {
        collectionReference
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    return@addSnapshotListener
                }

                snapshot?.documents?.let { documents ->
                    val list = arrayListOf<User>()
                    documents.map { document ->
                        document.toObject(User::class.java)?.let { o ->
                            list.add(o)
                        }
                    }

                    if (list.isNotEmpty()) {
                        subject.onNext(list)
                    }
                }
            }
    }

    fun get(ref: DocumentReference, onUserReceived: (User) -> Unit) {
        collectionReference.document(ref.id).get().addOnSuccessListener { snapshot ->
            snapshot?.let { document ->
                document.toObject(User::class.java)?.let { o ->
                    onUserReceived(o)
                }
            }
        }
    }

    fun create(
        user: User,
        onSuccessListener: (propertyId: String, ref: DocumentReference) -> Unit
    ) {
        collectionReference
            .add(user)
            .addOnSuccessListener { documentReference ->
                onSuccessListener(collectionReference.id, documentReference)
            }
    }

    companion object {
        private const val COLLECTION_NAME = "user"
    }
}