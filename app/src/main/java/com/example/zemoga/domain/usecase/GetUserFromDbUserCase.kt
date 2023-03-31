package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.repository.Repository

class GetUserFromDbUserCase(
    private val repository: Repository
) {
    operator fun invoke(id: Int) = repository.getUserFromDb(id)
}