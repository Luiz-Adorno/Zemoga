package com.example.zemoga.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentItem(
    val body: String,
    val email: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val postId: Int
)