package com.example.eventexpress.di

import android.app.Application
import androidx.room.Room
import com.example.eventexpress.data.local.event.EventsDatabase
import com.example.eventexpress.data.local.user.UserDatabase
import com.example.eventexpress.data.remote.EventApi
import com.example.eventexpress.data.remote.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideEventDatabase(
        app : Application
    ) : EventsDatabase {
        return Room.databaseBuilder(
            app,
            EventsDatabase::class.java,
            "events.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEventsApi() = EventApi()

    @Provides
    @Singleton
    fun provideUserApi() = UsersApi()

    @Provides
    @Singleton
    fun provideUserDatabase(
        app : Application
    ) : UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user.db"
        ).build()
    }
}