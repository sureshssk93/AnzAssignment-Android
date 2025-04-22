package anz.project.userapp.viewmodel

import anz.project.domain.model.User
import anz.project.domain.usecase.GetUserDetailUseCase
import anz.project.userapp.viewmodel.UserDetailViewModel.UserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModelTest {

    private lateinit var viewModel: UserDetailViewModel
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getUserDetailUseCase = mock()
        viewModel = UserDetailViewModel(getUserDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitLoadingState() = runTest {
        val initialState = viewModel.user.value
        assertEquals(UserState.Loading, initialState)
    }

    @Test
    fun testUserDataState() = runTest {
        // Given
        val userId = 1
        val mockUser = User(
            id = userId,
            name = "John Doe",
            company = "ANZ",
            username = "johndoe",
            email = "john@example.com",
            address = "123 Main St",
            zip = "12345",
            state = "NSW",
            country = "Australia",
            phone = "1234567890",
            photo = "photo.jpg"
        )
        whenever(getUserDetailUseCase(userId)).thenReturn(mockUser)

        // When
        viewModel.loadUser(userId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.user.first()
        assert(state is UserState.Success)
        assertEquals(mockUser, (state as UserState.Success).user)
    }

    @Test
    fun testUserDataErrorState() = runTest {
        // Given
        val userId = 1
        val errorMessage = "User not found"
        whenever(getUserDetailUseCase(userId)).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel.loadUser(userId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.user.first()
        assert(state is UserState.Error)
        assertEquals(errorMessage, (state as UserState.Error).message)
    }

    @Test
    fun testUserDataReloadState() = runTest {
        // Given
        val userId = 1
        val mockUser = User(
            id = userId,
            name = "John Doe",
            company = "ANZ",
            username = "johndoe",
            email = "john@example.com",
            address = "123 Main St",
            zip = "12345",
            state = "NSW",
            country = "Australia",
            phone = "1234567890",
            photo = "photo.jpg"
        )
        whenever(getUserDetailUseCase(userId)).thenReturn(mockUser)

        // When
        viewModel.loadUser(userId)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Load the same user again
        viewModel.loadUser(userId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        // Verify that getUserDetailUseCase was called only once
        org.mockito.kotlin.verify(getUserDetailUseCase, org.mockito.kotlin.times(1)).invoke(userId)
    }
} 