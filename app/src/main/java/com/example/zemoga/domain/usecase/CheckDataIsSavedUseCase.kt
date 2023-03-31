package com.example.zemoga.domain.usecase

import com.example.zemoga.domain.repository.Repository

class CheckDataIsSavedUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.checkHasTable()
}