package com.example.zemoga.data_source.remote.api

import com.example.zemoga.data_source.remote.models.CommentItem
import com.example.zemoga.data_source.remote.models.PostListItem
import com.example.zemoga.data_source.remote.models.UserItem
import javax.inject.Inject

class ApiServiceImpl
    @Inject constructor(
    private val retroService: RetroService
) {
    suspend fun getPost(): List<PostListItem> = retroService.getPost()
    suspend fun getComment(): List<CommentItem> = retroService.getComment()
    suspend fun getUser(): List<UserItem> = retroService.getUser()
}