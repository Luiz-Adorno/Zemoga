package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.repository.Repository

class GetPostFromDbUserCase(
    private val repository: Repository
) {
    operator fun invoke(id: Int) = repository.getPostFromDb(id)
}