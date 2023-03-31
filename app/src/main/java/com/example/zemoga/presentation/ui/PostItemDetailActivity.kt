package com.example.zemoga.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.zemoga.databinding.ActivityPostItemDetailBinding

class PostItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getIntExtra("post_id", -1)
        Log.d("dsa","dassadsda $postId")
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PostItemDetailActivity::class.java)
    }
}