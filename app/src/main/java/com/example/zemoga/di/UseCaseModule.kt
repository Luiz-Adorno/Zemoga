package com.example.zemoga.di

import com.example.zemoga.domain.repository.Repository
import com.example.zemoga.domain.usecase.GetAllPotsFromRemoteUseCase
import com.example.zemoga.domain.usecase.RootUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: Repository): RootUseCases {
        return RootUseCases(
            getAllPotsFromRemoteUseCase = GetAllPotsFromRemoteUseCase(repository),
        )
    }
}