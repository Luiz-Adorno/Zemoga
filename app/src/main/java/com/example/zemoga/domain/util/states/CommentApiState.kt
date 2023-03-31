package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.CommentItem

sealed class CommentApiState{
    object Loading : CommentApiState()
    class Failure(val msg:Throwable) : CommentApiState()
    class Success(val data:List<CommentItem>) : CommentApiState()
    object Empty : CommentApiState()
}
