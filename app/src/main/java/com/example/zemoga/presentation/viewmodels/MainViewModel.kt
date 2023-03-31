package com.example.zemoga.presentation.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.states.CommentApiState
import com.example.zemoga.domain.util.states.PostApiState
import com.example.zemoga.domain.util.states.UserApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    val receiverPostStateFlow: StateFlow<PostApiState> = postStateFlow

    private fun getAllPotsFromRemote() = viewModelScope.launch {
        postStateFlow.value = PostApiState.Loading
        rootUseCases.getAllPostFromRemoteUseCase().catch { e ->
            postStateFlow.value = PostApiState.Failure(e)
        }.collect { data ->
            postStateFlow.value = PostApiState.Success(data)
            savePostsInDatabase(data)
        }
    }

    private fun getAllCommentsFromRemote() = viewModelScope.launch {
        rootUseCases.getAllCommentsFromRemoteUseCase().catch { e ->
            Log.d("MainViewModel", "getAllCommentsFromRemote failure: $e ")
        }.collect { data ->
           saveCommentsInDatabase(data)
        }
    }

    private fun getAllUsersFromRemote() = viewModelScope.launch {
        rootUseCases.getAllUserFromRemoteUseCase().catch { e ->
            Log.d("MainViewModel", "getAllCommentsFromRemote failure: $e ")
        }.collect { data ->
           saveUsersInDatabase(data)
        }
    }

    private fun savePostsInDatabase(list: List<PostListItem>) {
        viewModelScope.launch {
            rootUseCases.savePostsInDatabaseUseCase(list)
        }
    }

    private fun saveCommentsInDatabase(list: List<CommentItem>) {
        viewModelScope.launch {
            rootUseCases.saveCommentsInDatabaseUseCase(list)
        }
    }

    private fun saveUsersInDatabase(list: List<UserItem>) {
        viewModelScope.launch {
            rootUseCases.saveUsersInDatabaseUseCase(list)
        }
    }

    private fun getSavedPosts() = viewModelScope.launch {
        postStateFlow.value = PostApiState.Loading
        rootUseCases.getAllPostFromLocalUseCase().catch { e ->
            postStateFlow.value = PostApiState.Failure(e)
        }.collect { data ->
            postStateFlow.value = PostApiState.Success(data)
        }
    }

    fun checkIfDataIsSavedInDatabase() = viewModelScope.launch(Dispatchers.IO) {
        val saved = rootUseCases.checkDataIsSavedUseCase()
        if(saved) {
            getSavedPosts()
        } else {
            getAllPotsFromRemote()
            getAllUsersFromRemote()
            getAllCommentsFromRemote()
        }
    }
}