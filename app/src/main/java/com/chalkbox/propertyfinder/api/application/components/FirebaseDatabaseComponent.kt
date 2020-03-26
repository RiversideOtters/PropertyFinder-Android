package com.chalkbox.propertyfinder.api.application.components

import com.chalkbox.propertyfinder.api.application.modules.FirebaseDatabaseModule
import com.chalkbox.propertyfinder.api.application.scopes.FirebaseDatabaseScope
import com.chalkbox.propertyfinder.api.firebase.database.CountryApi
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.api.firebase.database.TenantApi
import com.chalkbox.propertyfinder.api.firebase.database.UserApi
import dagger.Component

@FirebaseDatabaseScope
@Component(modules = [FirebaseDatabaseModule::class])
interface FirebaseDatabaseComponent {
    fun inject(arg: TenantApi)
    fun inject(arg: PropertyApi)
    fun inject(arg: CountryApi)
    fun inject(arg: UserApi)
}
