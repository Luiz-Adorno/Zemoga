package com.example.zemoga.presentation.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.states.PostApiState
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

    val receiverPostStateFlow: StateFlow<PostApiState> = postStateFlow

    fun getPostFromDb(id: Int) = viewModelScope.launch {
        rootUseCases.getPostFromDbUserCase(id).catch { e ->
            Log.d("DetailPostViewModel", "getPostFromDb failure: $e ")
        }.collect { data ->
            postStateFlow.value = PostApiState.Success(data)
        }
    }
}