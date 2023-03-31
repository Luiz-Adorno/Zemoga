package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.repository.Repository

class GetAllCommentsFromRemoteUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getCommentsFromRemote()

}