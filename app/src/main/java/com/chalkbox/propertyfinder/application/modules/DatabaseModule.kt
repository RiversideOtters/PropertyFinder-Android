package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.api.firebase.database.CountryApi
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.api.firebase.database.TenantApi
import com.chalkbox.propertyfinder.api.firebase.database.UserApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun tenantApi(): TenantApi = TenantApi()

    @Provides
    @Singleton
    fun propertyApi(): PropertyApi = PropertyApi()

    @Provides
    @Singleton
    fun countryApi(): CountryApi = CountryApi()

    @Provides
    @Singleton
    fun userApi(): UserApi = UserApi()
}