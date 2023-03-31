package com.example.zemoga.domain.util.states

import com.example.zemoga.data.models.UserItem

sealed class UserApiState{
    object Loading : UserApiState()
    class Failure(val msg:Throwable) : UserApiState()
    class Success(val data:List<UserItem>) : UserApiState()
    object Empty : UserApiState()
}
