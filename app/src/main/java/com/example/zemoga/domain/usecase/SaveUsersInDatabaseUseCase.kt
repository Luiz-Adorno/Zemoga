package com.example.zemoga.domain.usecase

import com.example.zemoga.data.models.UserItem
import com.example.zemoga.domain.repository.Repository

class SaveUsersInDatabaseUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(list: List<UserItem>) = repository.insertUsersInDatabase(list)
}