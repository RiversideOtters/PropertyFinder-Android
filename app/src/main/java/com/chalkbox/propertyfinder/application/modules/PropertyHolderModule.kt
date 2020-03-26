package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.property.details.PropertyHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PropertyHolderModule {
    @Provides
    @Singleton
    fun propertyHolder(): PropertyHolder = PropertyHolder()
}
