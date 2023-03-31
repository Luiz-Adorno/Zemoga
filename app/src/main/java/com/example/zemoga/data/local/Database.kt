package com.example.zemoga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem

@Database(entities = [PostListItem::class, CommentItem::class, UserItem::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract val contentDao: ContentDao

    companion object {
        const val DATABASE_NAME = "content_db"
    }
}