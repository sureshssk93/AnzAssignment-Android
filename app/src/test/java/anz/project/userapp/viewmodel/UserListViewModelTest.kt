package anz.project.userapp.viewmodel

import anz.project.domain.model.User
import anz.project.domain.usecase.GetUsersUseCase
import anz.project.userapp.viewmodel.UserListViewModel.UserListState
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
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel
    private lateinit var getUsersUseCase: GetUsersUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getUsersUseCase = mock()
        viewModel = UserListViewModel(getUsersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUserListLoadState() = runTest {
        val mockUsers = listOf(
            User(
                id = 1,
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
        )
        whenever(getUsersUseCase()).thenReturn(mockUsers)

        viewModel = UserListViewModel(getUsersUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.users.first()
        assert(state is UserListState.Success)
        assertEquals(mockUsers, (state as UserListState.Success).users)
    }

    @Test
    fun testUserListLoadErrorState() = runTest {
        // Given
        val errorMessage = "Network error"
        whenever(getUsersUseCase()).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel = UserListViewModel(getUsersUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.users.first()
        assert(state is UserListState.Error)
        assertEquals(errorMessage, (state as UserListState.Error).message)
    }
} 