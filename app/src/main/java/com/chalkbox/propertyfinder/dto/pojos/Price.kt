package com.chalkbox.propertyfinder.dto.pojos

import android.os.Parcelable
import com.chalkbox.propertyfinder.dto.pojos.base.Pojo
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
class Price(
    var currency: String = "",
    var rate: Map<String, Int> = mapOf()
) : Pojo, Parcelable {

    val daily
        @Exclude get() = rate[DAY_RATE] ?: 0
    val weekly
        @Exclude get() = rate[WEEK_RATE] ?: 0
    val monthly
        @Exclude get() = rate[MONTH_RATE] ?: 0

    companion object {
        private const val DAY_RATE = "day"
        private const val WEEK_RATE = "week"
        private const val MONTH_RATE = "month"
    }
}