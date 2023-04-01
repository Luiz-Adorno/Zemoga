package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.CommentItem

sealed class GetCommentState{
    object Loading : GetCommentState()
    class Failure(val msg:Throwable) : GetCommentState()
    class Success(val data:List<CommentItem>) : GetCommentState()
    object Empty : GetCommentState()
}
