package com.example.zemoga.domain.usecase

import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.domain.repository.Repository

class DeletePostUserCase(
    private val repository: Repository
) {
    suspend operator fun invoke(post: PostListItem): Int = repository.deletePost(post)
}