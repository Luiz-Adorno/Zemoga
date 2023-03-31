package com.example.zemoga.domain.usecase

data class RootUseCases(
    val getAllPostFromRemoteUseCase: GetAllPostFromRemoteUseCase,
    val getAllCommentsFromRemoteUseCase: GetAllCommentsFromRemoteUseCase,
    val getAllUserFromRemoteUseCase: GetAllUsersFromRemoteUseCase
)
