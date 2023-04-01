package com.example.zemoga.domain.util

import android.content.Context
import com.example.zemoga.presentation.ui.MainActivity
import com.example.zemoga.presentation.ui.PostItemDetailActivity
import javax.inject.Inject

class Navigator @Inject constructor() : Navigation {
    override fun openDetailsActivity(context: Context, postId: Int) {
        val activateIntent = PostItemDetailActivity.newIntent(context = context)
        activateIntent.putExtra("post_id", postId)
        context.startActivity(activateIntent)
    }

    override fun openMainActivity(context: Context, cameFromDeletedPost: Boolean) {
        val activateIntent = MainActivity.newIntent(context = context)
        activateIntent.putExtra("post_deleted", true)
        context.startActivity(activateIntent)
    }
}

interface Navigation {
    fun openDetailsActivity(context: Context, postId: Int)

    fun openMainActivity(context: Context, cameFromDeletedPost: Boolean)
}