package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.PostListItem

sealed class PostApiState{
    object Loading : PostApiState()
    class Failure(val msg:Throwable) : PostApiState()
    class Success(val data:List<PostListItem>) : PostApiState()
    object Empty : PostApiState()
}
