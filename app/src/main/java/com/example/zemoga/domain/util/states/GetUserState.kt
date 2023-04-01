package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.UserItem

sealed class GetUserState{
    object Loading : GetUserState()
    class Failure(val msg:Throwable) : GetUserState()
    class Success(val data:UserItem) : GetUserState()
    object Empty : GetUserState()
}
