package com.chalkbox.propertyfinder.application.modules

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PicassoModule {
    @Provides
    @Singleton
    fun picasso(): Picasso = Picasso.get()
}
