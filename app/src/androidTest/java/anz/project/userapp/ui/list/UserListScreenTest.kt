package anz.project.userapp.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import anz.project.domain.model.User
import anz.project.userapp.viewmodel.UserListViewModel
import anz.project.userapp.viewmodel.UserListViewModel.UserListState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class UserListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingUi() {
        val viewModel = mock<UserListViewModel>()
        whenever(viewModel.users).thenReturn(MutableStateFlow(UserListState.Loading))

        // When
        composeTestRule.setContent {
            UserListScreen(
                viewModel = viewModel,
                onUserClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Loading indicator").assertIsDisplayed()
    }

    @Test
    fun testUserLoadingSuccess() {

        val viewModel = mock<UserListViewModel>()
        val users = listOf(
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
        whenever(viewModel.users).thenReturn(MutableStateFlow(UserListState.Success(users)))


        composeTestRule.setContent {
            UserListScreen(
                viewModel = viewModel,
                onUserClick = {}
            )
        }


        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("john@example.com").assertIsDisplayed()
    }

    @Test
    fun testUserLoadingError() {

        val viewModel = mock<UserListViewModel>()
        val errorMessage = "Failed to load users"
        whenever(viewModel.users).thenReturn(MutableStateFlow(UserListState.Error(errorMessage)))

        // When
        composeTestRule.setContent {
            UserListScreen(
                viewModel = viewModel,
                onUserClick = {}
            )
        }


        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun testUserListClick() {
        // Given
        val viewModel = mock<UserListViewModel>()
        val users = listOf(
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
        whenever(viewModel.users).thenReturn(MutableStateFlow(UserListState.Success(users)))
        
        var clickedUserId: Int? = null
        val onUserClick: (Int) -> Unit = { userId -> clickedUserId = userId }


        composeTestRule.setContent {
            UserListScreen(
                viewModel = viewModel,
                onUserClick = onUserClick
            )
        }
        
        composeTestRule.onNodeWithText("John Doe").performClick()


        assert(clickedUserId == 1)
    }
} 