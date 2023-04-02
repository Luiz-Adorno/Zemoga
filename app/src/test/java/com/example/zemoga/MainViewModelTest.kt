package com.example.zemoga

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.zemoga.domain.repository.Repository
import com.example.zemoga.domain.usecase.RootUseCases
import com.example.zemoga.domain.util.PostFactory
import com.example.zemoga.presentation.viewmodels.MainViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val rootUseCases = mockk<RootUseCases>()

    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = instantiateViewModel()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel fetches data then it should call the repository`(){


        val mockedList = PostFactory.post
        every { rootUseCases.getAllPostFromRemoteUseCase() } returns MutableStateFlow(mockedList)

        viewModel.getAllPotsFromRemote()

        verify { rootUseCases.getAllPostFromRemoteUseCase() }

    }

    @Test
    fun `when viewModel fetches data from local then it should call the repository`(){

        val mockedList = PostFactory.post
        every { rootUseCases.getAllPostFromLocalUseCase() } returns MutableStateFlow(mockedList)

        viewModel.getSavedPosts()

        verify { rootUseCases.getAllPostFromLocalUseCase() }

    }

    private fun instantiateViewModel(): MainViewModel{
        return MainViewModel(rootUseCases)
    }

}
