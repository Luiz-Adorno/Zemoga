package com.example.zemoga.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.zemoga.databinding.ActivityMainBinding
import com.example.zemoga.domain.util.states.CommentApiState
import com.example.zemoga.domain.util.states.PostApiState
import com.example.zemoga.domain.util.states.UserApiState
import com.example.zemoga.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPostFromRemote()
        loadUsersFromRemote()
        loadCommentsFromRemote()
    }

    private fun loadPostFromRemote(){
        mainViewModel.getAllPotsFromRemote()
        lifecycleScope.launch {
            mainViewModel.receiverPostStateFlow.collect {
                when (it) {
                    is PostApiState.Loading -> {
                       // binding.recyclerView.isVisible = false
                        binding.progressMain.isVisible = true
                    }
                    is PostApiState.Failure -> {
                      //  binding.recyclerView.isVisible = false
                        binding.progressMain.isVisible = false
                        Log.d("MainActivity", "onCreate: ${it.msg} ")
                    }
                    is PostApiState.Success -> {
                      //  binding.recyclerView.isVisible = true
                        binding.progressMain.isVisible = false
                       // postAdapter.setData(it.data)
                    }
                    PostApiState.Empty -> {

                    }
                }
            }
        }
    }

    private fun loadUsersFromRemote(){
        mainViewModel.getAllUsersFromRemote()
        lifecycleScope.launch {
            mainViewModel.receiverUserStateFlow.collect {
                when (it) {
                    is UserApiState.Loading -> {
                        // binding.recyclerView.isVisible = false
                        binding.progressMain.isVisible = true
                    }
                    is UserApiState.Failure -> {
                        //  binding.recyclerView.isVisible = false
                        binding.progressMain.isVisible = false
                        Log.d("MainActivity", "onCreate: ${it.msg} ")
                    }
                    is UserApiState.Success -> {
                        //  binding.recyclerView.isVisible = true
                        binding.progressMain.isVisible = false
                        // postAdapter.setData(it.data)
                    }
                    UserApiState.Empty -> {
                    }
                }
            }
        }
    }

    private fun loadCommentsFromRemote(){
        mainViewModel.getAllCommentsFromRemote()
        lifecycleScope.launch {
            mainViewModel.receiverCommentStateFlow.collect {
                when (it) {
                    is CommentApiState.Loading -> {
                        // binding.recyclerView.isVisible = false
                        binding.progressMain.isVisible = true
                    }
                    is CommentApiState.Failure -> {
                        //  binding.recyclerView.isVisible = false
                        binding.progressMain.isVisible = false
                        Log.d("MainActivity", "onCreate: ${it.msg} ")
                    }
                    is CommentApiState.Success -> {
                        //  binding.recyclerView.isVisible = true
                        binding.progressMain.isVisible = false
                        // postAdapter.setData(it.data)
                    }
                    CommentApiState.Empty -> {
                    }
                }
            }
        }
    }
}