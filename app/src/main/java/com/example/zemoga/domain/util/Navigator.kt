package com.example.zemoga.domain.util

import android.content.Context
import com.example.zemoga.presentation.ui.PostItemDetailActivity
import javax.inject.Inject

class Navigator @Inject constructor() : Navigation {
    override fun openDetailsActivity(context: Context, postId: Int) {
        val activateIntent = PostItemDetailActivity.newIntent(context = context)
        activateIntent.putExtra("post_id", postId)
        context.startActivity(activateIntent)
    }
}

interface Navigation {
    fun openDetailsActivity(context: Context, postId: Int)
}