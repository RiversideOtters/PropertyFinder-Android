package com.chalkbox.propertyfinder.api.firebase.database

import com.chalkbox.propertyfinder.api.application.componentfactory.FirebaseComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.Tenant
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TenantApi : DatabaseApi<Tenant> {

    @Inject
    internal lateinit var firebaseFirestore: FirebaseFirestore

    private var collectionReference: CollectionReference

    private val subject = BehaviorSubject.create<List<Tenant>>()
    override val observable: Observable<List<Tenant>> = subject

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
                    val list = arrayListOf<Tenant>()
                    documents.map { document ->
                        document.toObject(Tenant::class.java)?.let { o ->
                            list.add(o)
                        }
                    }

                    if (list.isNotEmpty()) {
                        subject.onNext(list)
                    }
                }
            }
    }

    companion object {
        private const val COLLECTION_NAME = "tenant"
    }
}