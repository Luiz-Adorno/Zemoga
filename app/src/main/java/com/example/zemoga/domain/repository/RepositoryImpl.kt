package com.example.zemoga.domain.repository

import com.example.zemoga.data.remote.api.RetroService
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl(
    private val retroService: RetroService
): Repository {

    override fun getPostsFromRemote(): Flow<List<PostListItem>> = flow {
        emit(retroService.getPost())
    }.flowOn(Dispatchers.IO)

    override fun getCommentsFromRemote(): Flow<List<CommentItem>> = flow {
        emit(retroService.getComment())
    }.flowOn(Dispatchers.IO)

    override fun getUsersFromRemote(): Flow<List<UserItem>> = flow {
        emit(retroService.getUser())
    }.flowOn(Dispatchers.IO)
}