package com.chalkbox.propertyfinder.api.firebase.database

import com.chalkbox.propertyfinder.api.application.componentfactory.FirebaseComponentFactory
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class PropertyApi : DatabaseApi<Property> {

    @Inject
    internal lateinit var firebaseFirestore: FirebaseFirestore

    private var collectionReference: CollectionReference

    private val subject = BehaviorSubject.create<List<Property>>()
    override val observable: Observable<List<Property>> = subject

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
                    val list = arrayListOf<Property>()
                    documents.map { document ->
                        document.toObject(Property::class.java)?.let { o ->
                            list.add(o)
                        }
                    }

                    if (list.isNotEmpty()) {
                        subject.onNext(list)
                    }
                }
            }
    }

    fun create(
        property: Property,
        onSuccessListener: (propertyId: String, ref: DocumentReference) -> Unit
    ) {
        collectionReference
            .add(property)
            .addOnSuccessListener { documentReference ->
                onSuccessListener(collectionReference.id, documentReference)
            }
    }

    companion object {
        private const val COLLECTION_NAME = "property"
    }
}