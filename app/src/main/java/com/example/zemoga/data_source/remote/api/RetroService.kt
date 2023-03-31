package com.example.zemoga.data_source.remote.api

import com.example.zemoga.data_source.remote.models.CommentItem
import com.example.zemoga.data_source.remote.models.PostListItem
import com.example.zemoga.data_source.remote.models.UserItem
import com.example.zemoga.util.Constants
import retrofit2.http.GET

interface RetroService {
    @GET(Constants.POST_END_POINT)
    suspend fun getPost(): List<PostListItem>

    @GET(Constants.COMMENTS_END_POINT)
    suspend fun getComment(): List<CommentItem>

    @GET(Constants.USERS_END_POINT)
    suspend fun getUser(): List<UserItem>
}