package com.example.zemoga.domain.usecase

data class RootUseCases(
    val getAllPostFromRemoteUseCase: GetAllPostFromRemoteUseCase,
    val getAllCommentsFromRemoteUseCase: GetAllCommentsFromRemoteUseCase,
    val getAllUserFromRemoteUseCase: GetAllUsersFromRemoteUseCase,
    val savePostsInDatabaseUseCase: SavePostsInDatabaseUseCase,
    val saveCommentsInDatabaseUseCase: SaveCommentsInDatabaseUseCase,
    val saveUsersInDatabaseUseCase: SaveUsersInDatabaseUseCase,
    val getAllPostFromLocalUseCase: GetAllPostFromLocalUseCase,
    val checkDataIsSavedUseCase: CheckDataIsSavedUseCase,
    val getPostFromDbUserCase: GetPostFromDbUserCase,
    val getUserFromDbUserCase: GetUserFromDbUserCase,
    val getCommentsFromDbUserCase: GetCommentsFromDbUserCase,
    val deletePostUserCase: DeletePostUserCase,
    val updatePostUserCase: UpdatePostUserCase,
    val deleteAllExceptFavoritesUserCase: DeleteAllExceptFavoritesUserCase
)
