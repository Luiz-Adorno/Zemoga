package com.example.zemoga.presentation.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.states.GetCommentState
import com.example.zemoga.domain.util.states.GetPostState
import com.example.zemoga.domain.util.states.GetUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val postStateFlow: MutableStateFlow<GetPostState> = MutableStateFlow(GetPostState.Empty)
    private val userStateFlow: MutableStateFlow<GetUserState> = MutableStateFlow(GetUserState.Empty)
    private val commentStateFlow: MutableStateFlow<GetCommentState> = MutableStateFlow(GetCommentState.Empty)
    var deleteResult = MutableLiveData<Int>()

    val receiverPostStateFlow: StateFlow<GetPostState> = postStateFlow
    val receiverUserStateFlow: StateFlow<GetUserState> = userStateFlow
    val receiverCommentStateFlow: StateFlow<GetCommentState> = commentStateFlow

    fun getPostFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getPostFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            postStateFlow.value = GetPostState.Success(data)
            getUserFromDb(data.userId)
            getCommentsFromDb(data.id)
        }
    }

    private fun getUserFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getUserFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            userStateFlow.value = GetUserState.Success(data)
        }
    }

    private fun getCommentsFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getCommentsFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            commentStateFlow.value = GetCommentState.Success(data)
        }
    }

    fun deletePost(post: PostListItem) = viewModelScope.launch(Dispatchers.IO) {
        deleteResult.postValue(rootUseCases.deletePostUserCase.invoke(post))
    }
}