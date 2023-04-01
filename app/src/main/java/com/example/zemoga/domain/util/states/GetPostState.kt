package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.PostListItem

sealed class GetPostState{
    object Loading : GetPostState()
    class Failure(val msg:Throwable) : GetPostState()
    class Success(val data:PostListItem) : GetPostState()
    object Empty : GetPostState()
}
