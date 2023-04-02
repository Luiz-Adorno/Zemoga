package com.example.zemoga.domain.repository

import com.example.zemoga.data.local.ContentDao
import com.example.zemoga.data.remote.api.RetroService
import com.example.zemoga.data.models.CommentItem
import com.example.zemoga.data.models.PostListItem
import com.example.zemoga.data.models.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl(
    private val retroService: RetroService,
    private val contentDao: ContentDao
): Repository {

    override fun getPostsFromRemote(): Flow<List<PostListItem>> = flow {
        emit(retroService.getPost())
    }.flowOn(Dispatchers.IO)

    override fun getCommentsFromRemote(): Flow<List<CommentItem>> = flow {
        emit(retroService.getComment())
    }.flowOn(Dispatchers.IO)

    override fun getUsersFromRemote(): Flow<List<UserItem>> = flow {
        emit(retroService.getUser())
    }.flowOn(Dispatchers.IO)

    override suspend fun insertPostsInDatabase(list: List<PostListItem>) {
        contentDao.insertPost(list)
    }

    override suspend fun insertCommentsInDatabase(list: List<CommentItem>) {
        contentDao.insertComments(list)
    }

    override suspend fun insertUsersInDatabase(list: List<UserItem>) {
        contentDao.insertUsers(list)
    }

    override fun getAllPostFromLocal(): Flow<List<PostListItem>> = flow {
        emit(contentDao.getPosts())
    }.flowOn(Dispatchers.IO)

    override suspend fun checkHasTable(): Boolean {
       return contentDao.hasTable()
    }

    override fun getPostFromDb(id: Int): Flow<PostListItem> = flow {
        emit(contentDao.getPost(id))
    }.flowOn(Dispatchers.IO)

    override fun getUserFromDb(id: Int): Flow<UserItem> = flow {
        emit(contentDao.getUser(id))
    }.flowOn(Dispatchers.IO)

    override fun getCommentsFromDb(id: Int): Flow<List<CommentItem>> = flow {
        emit(contentDao.getComments(id))
    }.flowOn(Dispatchers.IO)

    override suspend fun deletePost(postListItem: PostListItem): Int {
        return contentDao.deletePost(postListItem)
    }

    override suspend fun updatePost(postListItem: PostListItem): Int {
        return contentDao.updatePost(postListItem)
    }

}