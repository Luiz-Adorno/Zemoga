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

        mainViewModel.checkIfDataIsSavedInDatabase()

        loadPost()

    }

    private fun loadPost(){
        lifecycleScope.launch {
            mainViewModel.receiverPostStateFlow.collect {
                when (it) {
                    is PostApiState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is PostApiState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("MainActivity", "onCreate: ${it.msg} ")
                    }
                    is PostApiState.Success -> {
                        binding.progressMain.isVisible = false
                    }
                    PostApiState.Empty -> {

                    }
                }
            }
        }
    }
}