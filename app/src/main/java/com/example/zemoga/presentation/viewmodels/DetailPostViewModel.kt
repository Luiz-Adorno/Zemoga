package com.example.zemoga.presentation.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.states.CommentApiState
import com.example.zemoga.domain.util.states.PostApiState
import com.example.zemoga.domain.util.states.UserApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPostViewModel
@Inject
constructor(
    private val rootUseCases: RootUseCases
): ViewModel() {

    private val postStateFlow: MutableStateFlow<PostApiState> = MutableStateFlow(PostApiState.Empty)
    private val userStateFlow: MutableStateFlow<UserApiState> = MutableStateFlow(UserApiState.Empty)
    private val commentStateFlow: MutableStateFlow<CommentApiState> = MutableStateFlow(CommentApiState.Empty)

    val receiverPostStateFlow: StateFlow<PostApiState> = postStateFlow
    val receiverUserStateFlow: StateFlow<UserApiState> = userStateFlow
    val receiverCommentStateFlow: StateFlow<CommentApiState> = commentStateFlow

    fun getPostFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getPostFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            postStateFlow.value = PostApiState.Success(data)
            getUserFromDb(data.userId)
            getCommentsFromDb(data.id)
        }
    }

    private fun getUserFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getUserFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            userStateFlow.value = UserApiState.Success(data)
        }
    }

    private fun getCommentsFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getCommentsFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            commentStateFlow.value = CommentApiState.Success(data)
        }
    }
}