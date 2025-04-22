package anz.project.data.repository

import anz.project.data.db.UserDao
import anz.project.data.network.ApiService
import anz.project.data.network.UserResponse
import anz.project.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        apiService = mock()
        userDao = mock()
        repository = UserRepositoryImpl(apiService, userDao)
    }

    @Test
    fun `getAllUsers success returns users from API and saves to database`() = runTest {
        // Given
        val apiUsers = listOf(
            UserResponse(
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
        whenever(apiService.getUsers()).thenReturn(apiUsers)
        whenever(userDao.insertUsers(any())).thenReturn(Unit)

        // When
        val result = repository.getAllUsers()

        // Then
        assertEquals(1, result.size)
        assertEquals(apiUsers[0].id, result[0].id)
        assertEquals(apiUsers[0].name, result[0].name)
        verify(userDao).insertUsers(any())
    }

    @Test
    fun `getAllUsers API error returns users from database`() = runTest {
        // Given
        val dbUsers = listOf(
            anz.project.data.db.UserEntity(
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
        whenever(apiService.getUsers()).thenThrow(RuntimeException("Network error"))
        whenever(userDao.getUsers()).thenReturn(dbUsers)

        // When
        val result = repository.getAllUsers()

        // Then
        assertEquals(1, result.size)
        assertEquals(dbUsers[0].id, result[0].id)
        assertEquals(dbUsers[0].name, result[0].name)
        verify(userDao, never()).insertUsers(any())
    }

    @Test
    fun `getUserDetailById success returns user from API and saves to database`() = runTest {
        // Given
        val userId = 1
        val apiUser = UserResponse(
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
        whenever(apiService.getUserById(userId)).thenReturn(apiUser)
        whenever(userDao.insertUsers(any())).thenReturn(Unit)

        // When
        val result = repository.getUserDetailById(userId)

        // Then
        assertEquals(apiUser.id, result.id)
        assertEquals(apiUser.name, result.name)
        verify(userDao).insertUsers(any())
    }

    @Test
    fun `getUserDetailById API error returns user from database`() = runTest {
        // Given
        val userId = 1
        val dbUser = anz.project.data.db.UserEntity(
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
        whenever(apiService.getUserById(userId)).thenThrow(RuntimeException("Network error"))
        whenever(userDao.getUserById(userId)).thenReturn(dbUser)

        // When
        val result = repository.getUserDetailById(userId)

        // Then
        assertEquals(dbUser.id, result.id)
        assertEquals(dbUser.name, result.name)
        verify(userDao, never()).insertUsers(any())
    }

    @Test(expected = Exception::class)
    fun `getUserDetailById API error and user not in database throws exception`() = runTest {
        // Given
        val userId = 1
        whenever(apiService.getUserById(userId)).thenThrow(RuntimeException("Network error"))
        whenever(userDao.getUserById(userId)).thenReturn(null)

        // When
        repository.getUserDetailById(userId)

        // Then
        // Exception is expected
    }
} 