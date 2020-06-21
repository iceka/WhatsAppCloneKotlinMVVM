package com.iceka.whatsappclonekotlin.di

import android.app.Application
import com.iceka.whatsappclonekotlin.data.firebase.FirebaseSourceUser
import com.iceka.whatsappclonekotlin.data.firebase.FirebaseSourceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideFirebaseSourceUser() = FirebaseSourceUser()

    @Singleton
    @Provides
    fun provideFirebaseSourceUtil() = FirebaseSourceUtil()

}

