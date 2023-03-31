package com.example.zemoga.presentation.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.states.PostsApiState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val postsStateFlow: MutableStateFlow<PostsApiState> = MutableStateFlow(PostsApiState.Empty)

    val receiverPostsStateFlow: StateFlow<PostsApiState> = postsStateFlow

    private fun getAllPotsFromRemote() = viewModelScope.launch {
        postsStateFlow.value = PostsApiState.Loading
        rootUseCases.getAllPostFromRemoteUseCase().catch { e ->
            postsStateFlow.value = PostsApiState.Failure(e)
        }.collect { data ->
            postsStateFlow.value = PostsApiState.Success(data)
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
        postsStateFlow.value = PostsApiState.Loading
        rootUseCases.getAllPostFromLocalUseCase().catch { e ->
            //if gets error for getting from the local, try get the data from the api
            getAllPotsFromRemote()
            postsStateFlow.value = PostsApiState.Failure(e)
        }.collect { data ->
            postsStateFlow.value = PostsApiState.Success(data)
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