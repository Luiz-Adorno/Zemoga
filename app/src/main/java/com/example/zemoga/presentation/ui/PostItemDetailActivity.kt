package com.example.zemoga.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zemoga.databinding.ActivityPostItemDetailBinding

class PostItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}