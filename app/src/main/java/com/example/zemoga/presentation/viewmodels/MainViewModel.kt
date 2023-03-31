package com.example.zemoga.presentation.viewmodels


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
class MainViewModel
@Inject
constructor(
    private val rootUseCases: RootUseCases
): ViewModel() {

    private val postStateFlow: MutableStateFlow<PostApiState> = MutableStateFlow(PostApiState.Empty)
    private val commentStateFlow: MutableStateFlow<CommentApiState> = MutableStateFlow(CommentApiState.Empty)
    private val userStateFlow: MutableStateFlow<UserApiState> = MutableStateFlow(UserApiState.Empty)

    val receiverPostStateFlow: StateFlow<PostApiState> = postStateFlow
    val receiverCommentStateFlow: StateFlow<CommentApiState> = commentStateFlow
    val receiverUserStateFlow: StateFlow<UserApiState> = userStateFlow

    fun getAllPotsFromRemote() = viewModelScope.launch {
        postStateFlow.value = PostApiState.Loading

        rootUseCases.getAllPostFromRemoteUseCase().catch { e ->
            postStateFlow.value = PostApiState.Failure(e)
        }.collect { data ->
            postStateFlow.value = PostApiState.Success(data)
        }
    }

    fun getAllCommentsFromRemote() = viewModelScope.launch {
        commentStateFlow.value = CommentApiState.Loading
        rootUseCases.getAllCommentsFromRemoteUseCase().catch { e ->
            commentStateFlow.value = CommentApiState.Failure(e)
        }.collect { data ->
            commentStateFlow.value = CommentApiState.Success(data)
        }
    }

    fun getAllUsersFromRemote() = viewModelScope.launch {
        userStateFlow.value = UserApiState.Loading
        rootUseCases.getAllUserFromRemoteUseCase().catch { e ->
            userStateFlow.value = UserApiState.Failure(e)
        }.collect { data ->
            userStateFlow.value = UserApiState.Success(data)
        }
    }
}