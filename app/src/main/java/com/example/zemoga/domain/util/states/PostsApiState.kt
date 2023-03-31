package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.PostListItem

sealed class PostsApiState{
    object Loading : PostsApiState()
    class Failure(val msg:Throwable) : PostsApiState()
    class Success(val data:List<PostListItem>) : PostsApiState()
    object Empty : PostsApiState()
}
