package com.example.zemoga.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemoga.R
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.databinding.ActivityPostItemDetailBinding
import com.example.zemoga.domain.util.Navigator
import com.example.zemoga.domain.util.states.GetCommentState
import com.example.zemoga.domain.util.states.GetPostState
import com.example.zemoga.domain.util.states.GetUserState
import com.example.zemoga.presentation.adapters.CommentAdapter
import com.example.zemoga.presentation.viewmodels.DetailPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostItemDetailBinding
    private lateinit var commentAdapter: CommentAdapter
    private val detailPostViewModel: DetailPostViewModel by viewModels()
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        val postId = intent.getIntExtra("post_id", -1)
        detailPostViewModel.getPostFromDb(postId)
        getPost()
    }

    private fun getPost() {
        lifecycleScope.launch {
            detailPostViewModel.receiverPostStateFlow.collect {
                when (it) {
                    is GetPostState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is GetPostState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "PostApiState: ${it.msg} ")
                    }
                    is GetPostState.Success -> {
                        binding.progressMain.isVisible = false
                        binding.title.text = it.data.title
                        binding.body.text = it.data.body
                        deletePost(it.data)
                        getUser()
                        getComments()
                    }
                    GetPostState.Empty -> {

                    }
                }
            }
        }
    }

    private fun deletePost(post: PostListItem) {
        binding.deleteIcon.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setTitle("Delete Post")
            dialogBuilder.setMessage("Click Ok to delete post ")
            dialogBuilder.setPositiveButton(R.string.ok) { dialog, _ ->
                detailPostViewModel.deletePost(post)
                binding.progressMain.isVisible = true
                detailPostViewModel.deleteResult.observe(this) {
                    //0 if no row deleted.
                    if(it == 0){
                        Toast.makeText(applicationContext, "Fail to delete post, please try again", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(applicationContext, "Post successfully deleted", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        navigator.openMainActivity(this)
                    }
                }

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
                    is GetUserState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is GetUserState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "UserApiState: ${it.msg} ")
                    }
                    is GetUserState.Success -> {
                        binding.progressMain.isVisible = false
                        binding.woner.text = it.data.name
                    }
                    GetUserState.Empty -> {

                    }
                }
            }
        }
    }

    private fun getComments() {
        lifecycleScope.launch {
            detailPostViewModel.receiverCommentStateFlow.collect {
                when (it) {
                    is GetCommentState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is GetCommentState.Failure -> {
                        binding.progressMain.isVisible = false
                        Log.d("PostItemDetailActivity", "CommentApiState: ${it.msg} ")
                    }
                    is GetCommentState.Success -> {
                        binding.recyclerView.isVisible = true
                        binding.progressMain.isVisible = false
                        commentAdapter.setData(it.data)
                        binding.commentNumber.text = commentAdapter.itemCount.toString()
                    }
                    GetCommentState.Empty -> {

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