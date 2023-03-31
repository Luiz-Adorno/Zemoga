package com.example.zemoga.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.PostApiState
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

    val _postStateFlow: StateFlow<PostApiState> = postStateFlow

    fun getPost() = viewModelScope.launch {
        postStateFlow.value = PostApiState.Loading

        rootUseCases.getAllPotsFromRemoteUseCase().catch { e ->
            postStateFlow.value = PostApiState.Failure(e)
        }.collect { data ->
            postStateFlow.value = PostApiState.Success(data)
        }
    }
}