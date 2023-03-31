package com.example.zemoga.domain.usecase

import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.domain.repository.Repository

class SaveCommentsInDatabaseUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(list: List<CommentItem>) = repository.insertCommentsInDatabase(list)
}