package com.chalkbox.propertyfinder.api.firebase.filters

import com.google.firebase.firestore.Query

class PropertyFilter: Filter() {
    init {
        orderBy[DEFAULT_ORDER_BY] = Query.Direction.ASCENDING
    }

    companion object {
        private const val DEFAULT_ORDER_BY = "availability"
    }
}