package com.example.eventexpress.di

import com.example.eventexpress.data.repository.DataRepositoryImp
import com.example.eventexpress.domain.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDataRepository(
        repository: DataRepositoryImp
    ) : DataRepository
}