package com.example.zemoga.presentation.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemoga.R
import com.example.zemoga.databinding.ActivityPostItemDetailBinding
import com.example.zemoga.domain.util.states.CommentApiState
import com.example.zemoga.domain.util.states.PostApiState
import com.example.zemoga.domain.util.states.UserApiState
import com.example.zemoga.presentation.adapters.CommentAdapter
import com.example.zemoga.presentation.viewmodels.DetailPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostItemDetailBinding
    private lateinit var commentAdapter: CommentAdapter
    private val detailPostViewModel: DetailPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        val postId = intent.getIntExtra("post_id", -1)
        detailPostViewModel.getPostFromDb(postId)
        getPost()
        deletePost()
    }

    private fun getPost() {
        lifecycleScope.launch {
            detailPostViewModel.receiverPostStateFlow.collect {
                when (it) {
                    is PostApiState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is PostApiState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "PostApiState: ${it.msg} ")
                    }
                    is PostApiState.Success -> {
                        binding.progressMain.isVisible = false
                        binding.title.text = it.data.title
                        binding.body.text = it.data.body
                        getUser()
                        getComments()
                    }
                    PostApiState.Empty -> {

                    }
                }
            }
        }
    }

    private fun deletePost() {
        binding.deleteIcon.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setTitle("Delete Post")
            dialogBuilder.setMessage("Click Ok to delete post ")
            dialogBuilder.setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            dialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            val b = dialogBuilder.create()
            b.show()

        }
    }

    private fun getUser() {
        lifecycleScope.launch {
            detailPostViewModel.receiverUserStateFlow.collect {
                when (it) {
                    is UserApiState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is UserApiState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "UserApiState: ${it.msg} ")
                    }
                    is UserApiState.Success -> {
                        binding.progressMain.isVisible = false
                        binding.woner.text = it.data.name
                    }
                    UserApiState.Empty -> {

                    }
                }
            }
        }
    }

    private fun getComments() {
        lifecycleScope.launch {
            detailPostViewModel.receiverCommentStateFlow.collect {
                when (it) {
                    is CommentApiState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is CommentApiState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "CommentApiState: ${it.msg} ")
                    }
                    is CommentApiState.Success -> {
                        binding.recyclerView.isVisible = true
                        binding.progressMain.isVisible = false
                        commentAdapter.setData(it.data)
                        binding.commentNumber.text = commentAdapter.itemCount.toString()
                    }
                    CommentApiState.Empty -> {

                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        commentAdapter = CommentAdapter(ArrayList())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PostItemDetailActivity)
            adapter = commentAdapter
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PostItemDetailActivity::class.java)
    }
}