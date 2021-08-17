package com.books.app.di

import android.app.Application
import android.content.Context
import com.books.app.firebase.FirebaseRemoteConfigRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfigRepository(): FirebaseRemoteConfigRepository =
        FirebaseRemoteConfigRepository()

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}