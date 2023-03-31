package com.example.zemoga.di

import com.example.zemoga.data.remote.api.RetroService
import com.example.zemoga.domain.repository.Repository
import com.example.zemoga.domain.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(retroService: RetroService): Repository {
        return RepositoryImpl(retroService)
    }
}