package com.example.zemoga.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.databinding.ActivityMainBinding
import com.example.zemoga.domain.util.Navigator
import com.example.zemoga.domain.util.states.GetPostsState
import com.example.zemoga.presentation.adapters.PostAdapter
import com.example.zemoga.presentation.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.checkIfDataIsSavedInDatabase()
        initRecyclerView()
        loadPost()
        floatButton()
        reloadDataFromApi()
        deleteAllExceptFavoriteOnes()
    }

    private fun loadPost() {
        lifecycleScope.launch {
            mainViewModel.receiverPostsStateFlow.collect { posts ->
                when (posts) {
                    is GetPostsState.Loading -> {
                        binding.progressMain.isVisible = true
                    }
                    is GetPostsState.Failure -> {
                        binding.progressMain.isVisible = false
                        Toast.makeText(
                            applicationContext,
                            "Fail to load data, check your internet connection",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("MainActivity", "onCreate: ${posts.msg} ")
                    }
                    is GetPostsState.Success -> {
                        binding.progressMain.isVisible = false
                        postAdapter.setData(posts.data)
                    }
                    GetPostsState.Empty -> {

                    }
                }
            }
        }
    }

    private fun reloadDataFromApi(){
        binding.reloadFromRemote.setOnClickListener { view ->
            mainViewModel.getAllPotsFromRemote()
            Snackbar.make(view,"Reload data from api", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    private fun floatButton(){
        binding.fab.setOnClickListener { view ->
            mainViewModel.getSavedPosts()
            Snackbar.make(view,"Refresh and sort list", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    private fun deleteAllExceptFavoriteOnes(){
        binding.deleteUnfavorites.setOnClickListener {
            mainViewModel.deleteAllExceptFavorites()
        }
        mainViewModel.deleteExceptFavoriteResult.observe(this){
            //0 if no row deleted.
            if(it == 0){
                Toast.makeText(applicationContext, "Fail to delete unfavorite post, please try again", Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.getSavedPosts()
            }
        }
    }

    private fun initRecyclerView() {
        postAdapter = PostAdapter(
            ArrayList(), this@MainActivity::openPostDetail,
            this@MainActivity::favoritePost
        )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun favoritePost(post: PostListItem) {
        post.isFavorite?.let {
            if (it) {
                post.isFavorite = false
                mainViewModel.updatePost(post)

            } else {
                post.isFavorite = true
                mainViewModel.updatePost(post)
            }
        }
        mainViewModel.updateResult.observe(this){
            //0 if no row updated.
            if(it == 0){
                Toast.makeText(applicationContext, "Fail to favorite/unfavorite post, please try again", Toast.LENGTH_SHORT).show()
            } else {
                postAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun openPostDetail(post: PostListItem) {
        navigator.openDetailsActivity(this, postId = post.id)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        val postDeleted = intent.getBooleanExtra("post_deleted", false)
        if (postDeleted) {
            postAdapter.notifyDataSetChanged()
        }
        super.onResume()
    }
}