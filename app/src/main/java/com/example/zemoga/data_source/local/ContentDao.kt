package com.example.zemoga.data_source.local

import androidx.room.*
import com.example.zemoga.data_source.remote.models.CommentItem
import com.example.zemoga.data_source.remote.models.PostListItem
import com.example.zemoga.data_source.remote.models.UserItem

@Dao
interface ContentDao {

    @Query("SELECT * FROM posts")
    fun getPosts(): List<PostListItem>

    @Query("SELECT * FROM posts WHERE id=:id")
    fun getPost(id: Int): List<PostListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(posts: List<PostListItem>)

    @Query("SELECT * FROM users WHERE id=:id")
    fun getUsers(id: Int): UserItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserItem>)

    @Query("SELECT * FROM comments WHERE postId=:postId")
    fun getComments(postId: Int): List<CommentItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentItem>)
}