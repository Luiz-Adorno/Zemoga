package com.example.zemoga.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "posts")
data class PostListItem(
    val body: String,
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
)