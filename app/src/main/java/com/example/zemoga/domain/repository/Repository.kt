package com.example.zemoga.domain.repository

import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getPostsFromRemote(): Flow<List<PostListItem>>

    fun getCommentsFromRemote(): Flow<List<CommentItem>>

    fun getUsersFromRemote(): Flow<List<UserItem>>

    suspend fun insertPostsInDatabase(list: List<PostListItem>)

    suspend fun insertCommentsInDatabase(list: List<CommentItem>)

    suspend fun insertUsersInDatabase(list: List<UserItem>)

    fun getAllPostFromLocal() : Flow<List<PostListItem>>

    suspend fun checkHasTable() : Boolean

}