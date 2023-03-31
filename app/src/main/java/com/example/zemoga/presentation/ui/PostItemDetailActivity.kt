package com.example.zemoga.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.zemoga.databinding.ActivityPostItemDetailBinding
import com.example.zemoga.domain.util.states.PostApiState
import com.example.zemoga.presentation.viewmodels.DetailPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class PostItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostItemDetailBinding
    private val detailPostViewModel: DetailPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getIntExtra("post_id", -1)
        detailPostViewModel.getPostFromDb(postId)
        getPost()
    }

    private fun getPost(){
        lifecycleScope.launch {
            detailPostViewModel.receiverPostStateFlow.collect {
                when (it) {
                    is PostApiState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is PostApiState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "onCreate: ${it.msg} ")
                    }
                    is PostApiState.Success -> {
                        binding.progressMain.isVisible = false
                        binding.title.text = it.data.title
                        binding.body.text = it.data.body                    }
                    PostApiState.Empty -> {

                    }
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PostItemDetailActivity::class.java)
    }
}