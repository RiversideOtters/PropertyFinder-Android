package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.api.firebase.filters.PropertyFilter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FilterModule {
    @Provides
    @Singleton
    fun propertyFilter(): PropertyFilter = PropertyFilter()
}
