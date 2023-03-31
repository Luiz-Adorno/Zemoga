package com.example.zemoga.domain.repository

import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getPostsFromRemote(): Flow<List<PostListItem>>

    fun getCommentsFromRemote(): Flow<List<CommentItem>>

    fun getUsersFromRemote(): Flow<List<UserItem>>

}