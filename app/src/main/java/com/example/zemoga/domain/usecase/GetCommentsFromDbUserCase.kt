package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.repository.Repository

class GetCommentsFromDbUserCase(
    private val repository: Repository
) {
    operator fun invoke(id: Int) = repository.getCommentsFromDb(id)
}