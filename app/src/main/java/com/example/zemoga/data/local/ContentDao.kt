package com.example.zemoga.data.local

import androidx.room.*
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem

@Dao
interface ContentDao {

    @Query("SELECT EXISTS(SELECT * FROM posts)")
    fun hasTable(): Boolean

    @Query("SELECT * FROM posts")
    fun getPosts(): List<PostListItem>

    @Query("SELECT * FROM posts WHERE id=:id")
    fun getPost(id: Int): PostListItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(posts: List<PostListItem>)

    @Query("SELECT * FROM users WHERE id=:id")
    fun getUser(id: Int): UserItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserItem>)

    @Query("SELECT * FROM comments WHERE postId=:postId")
    fun getComments(postId: Int): List<CommentItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentItem>)

    @Delete
    suspend fun deletePost(postListItem: PostListItem): Int

    @Update
    fun updatePost(postListItem: PostListItem): Int
}