package com.example.zemoga.di

import com.example.zemoga.domain.repository.Repository
import com.example.zemoga.domain.usecase.GetAllCommentsFromRemoteUseCase
import com.example.zemoga.domain.usecase.GetAllPostFromRemoteUseCase
import com.example.zemoga.domain.usecase.GetAllUsersFromRemoteUseCase
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
            getAllPostFromRemoteUseCase = GetAllPostFromRemoteUseCase(repository),
            getAllCommentsFromRemoteUseCase = GetAllCommentsFromRemoteUseCase(repository),
            getAllUserFromRemoteUseCase = GetAllUsersFromRemoteUseCase(repository)
        )
    }
}