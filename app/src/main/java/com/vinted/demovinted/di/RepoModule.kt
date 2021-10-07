package com.vinted.demovinted.di

import com.vinted.demovinted.data.repository.remote.RepositoryImpl
import com.vinted.demovinted.data.repository.remote.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}