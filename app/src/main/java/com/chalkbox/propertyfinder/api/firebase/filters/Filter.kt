package com.chalkbox.propertyfinder.api.firebase.filters

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query

abstract class Filter {
    protected val orderBy: MutableMap<String, Query.Direction> = mutableMapOf()
    private val filterBy: MutableMap<FieldPath, String> = mutableMapOf()

    fun clear() {
        orderBy.clear()
        filterBy.clear()
    }

    fun addFilter(property: FieldPath, value: String) {
        filterBy[property] = value
    }

    fun queryWithFilter(ref: CollectionReference): Query {
        var query: Query = ref
        for ((key, value) in orderBy.iterator()) {
            query = ref.orderBy(key, value)
        }
        for ((key, value) in filterBy.iterator()) {
            query = query.whereEqualTo(key, value)
        }
        return query
    }
}