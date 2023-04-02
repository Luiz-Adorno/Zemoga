package com.example.zemoga.domain.usecase

import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.domain.repository.Repository

class DeleteAllExceptFavoritesUserCase(
    private val repository: Repository
) {
    suspend operator fun invoke(): Int = repository.deleteAllExceptFavorites()
}