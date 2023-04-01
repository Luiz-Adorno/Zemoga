package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.PostListItem

sealed class GetPostsState{
    object Loading : GetPostsState()
    class Failure(val msg:Throwable) : GetPostsState()
    class Success(val data:List<PostListItem>) : GetPostsState()
    object Empty : GetPostsState()
}
