package com.chalkbox.propertyfinder.property.list

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import com.chalkbox.propertyfinder.api.firebase.filters.PropertyFilter
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PagingOptionsFactory(
    private val lifecycleOwner: LifecycleOwner,
    private val propertyFilter: PropertyFilter
) {
    private val fireStore = FirebaseFirestore.getInstance()
    private val collection = fireStore.collection(COLLECTION_NAME)

    private val query: Query
        get() = propertyFilter.queryWithFilter(collection)

    private val config
        get() = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(PRE_FETCH_DISTANCE)
            .setPageSize(FETCH_PAGE_SIZE)
            .build()

    val options: FirestorePagingOptions<Property>
        get() = FirestorePagingOptions.Builder<Property>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(query, config, Property::class.java)
            .build()

    companion object {
        private const val COLLECTION_NAME = "property"
        private const val FETCH_PAGE_SIZE = 10
        private const val PRE_FETCH_DISTANCE = 2

    }
}