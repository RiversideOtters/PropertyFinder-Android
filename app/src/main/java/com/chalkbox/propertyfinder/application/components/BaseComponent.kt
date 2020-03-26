package com.chalkbox.propertyfinder.application.components

import android.app.Application
import com.chalkbox.propertyfinder.MainApplication
import com.chalkbox.propertyfinder.ads.AdApi
import com.chalkbox.propertyfinder.ads.application.modules.AdModule
import com.chalkbox.propertyfinder.api.firebase.database.CountryApi
import com.chalkbox.propertyfinder.api.firebase.database.PropertyApi
import com.chalkbox.propertyfinder.api.firebase.database.TenantApi
import com.chalkbox.propertyfinder.api.firebase.database.UserApi
import com.chalkbox.propertyfinder.api.firebase.filters.PropertyFilter
import com.chalkbox.propertyfinder.api.firebase.storage.StorageApi
import com.chalkbox.propertyfinder.application.modules.*
import com.chalkbox.propertyfinder.dto.managers.UserAccountManager
import com.chalkbox.propertyfinder.feed.presenters.base.GroupPresenterFacade
import com.chalkbox.propertyfinder.property.create.imagePicker.ImageHolder
import com.chalkbox.propertyfinder.property.details.PropertyHolder
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        PropertyFinderApplicationModule::class,
        DatabaseModule::class,
        StorageModule::class,
        FeedModule::class,
        AdModule::class,
        UserAccountModule::class,
        PicassoModule::class,
        FilterModule::class,
        ImageHolderModule::class,
        PropertyHolderModule::class
    ]
)
interface BaseComponent {

    fun feedPresenterFacade(): GroupPresenterFacade

    // Managers
    fun userAccountManager(): UserAccountManager

    // Image
    fun picasso(): Picasso

    // Holders
    fun imageHolder(): ImageHolder

    fun propertyHolder(): PropertyHolder

    // API
    fun adApi(): AdApi

    fun userApi(): UserApi
    fun tenantApi(): TenantApi
    fun propertyApi(): PropertyApi
    fun countryApi(): CountryApi
    fun storageApi(): StorageApi

    // Filters
    fun propertyFilter(): PropertyFilter

    fun inject(arg: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): BaseComponent
    }
}