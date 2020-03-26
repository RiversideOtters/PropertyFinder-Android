package com.chalkbox.propertyfinder.application.modules

import com.chalkbox.propertyfinder.property.create.imagePicker.ImageHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageHolderModule {
    @Provides
    @Singleton
    fun imageHolder(): ImageHolder = ImageHolder()
}