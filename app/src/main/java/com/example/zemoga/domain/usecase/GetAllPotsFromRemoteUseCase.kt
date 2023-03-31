package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.repository.Repository

class GetAllPotsFromRemoteUseCase(
    private val repository: Repository
) {

    operator fun invoke() = repository.getPostsFromRemote()

}