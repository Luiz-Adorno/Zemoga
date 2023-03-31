package com.example.zemoga.data.remote.api

import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import com.example.zemoga.domain.util.Constants
import retrofit2.http.GET

interface RetroService {
    @GET(Constants.POST_END_POINT)
    suspend fun getPost(): List<PostListItem>

    @GET(Constants.COMMENTS_END_POINT)
    suspend fun getComment(): List<CommentItem>

    @GET(Constants.USERS_END_POINT)
    suspend fun getUser(): List<UserItem>
}