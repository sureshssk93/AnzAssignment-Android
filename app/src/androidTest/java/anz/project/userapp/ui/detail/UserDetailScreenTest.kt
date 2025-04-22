package anz.project.userapp.ui.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import anz.project.domain.model.User
import anz.project.userapp.viewmodel.UserDetailViewModel
import anz.project.userapp.viewmodel.UserDetailViewModel.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class UserDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUserDetailsLoadingUI() {
        val viewModel = mock<UserDetailViewModel>()
        whenever(viewModel.user).thenReturn(MutableStateFlow(UserState.Loading))


        composeTestRule.setContent {
            UserDetailScreen(
                userId = 1,
                viewModel = viewModel,
                modifier = androidx.compose.ui.Modifier
            )
        }


        composeTestRule.onNodeWithContentDescription("Loading indicator").assertIsDisplayed()
    }

    @Test
    fun testUserDetailsLoadSuccessUI() {

        val viewModel = mock<UserDetailViewModel>()
        val user = User(
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
        whenever(viewModel.user).thenReturn(MutableStateFlow(UserState.Success(user)))


        composeTestRule.setContent {
            UserDetailScreen(
                userId = 1,
                viewModel = viewModel,
                modifier = androidx.compose.ui.Modifier
            )
        }


        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("john@example.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("1234567890").assertIsDisplayed()
        composeTestRule.onNodeWithText("123 Main St").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANZ").assertIsDisplayed()
    }

    @Test
    fun testUserDetailsLoadErrorUi() {

        val viewModel = mock<UserDetailViewModel>()
        val errorMessage = "Failed to load user details"
        whenever(viewModel.user).thenReturn(MutableStateFlow(UserState.Error(errorMessage)))

        // When
        composeTestRule.setContent {
            UserDetailScreen(
                userId = 1,
                viewModel = viewModel,
                modifier = androidx.compose.ui.Modifier
            )
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun testUserDetailsWithId() {
        // Given
        val viewModel = mock<UserDetailViewModel>()
        val userId = 1
        whenever(viewModel.user).thenReturn(MutableStateFlow(UserState.Loading))

        composeTestRule.setContent {
            UserDetailScreen(
                userId = userId,
                viewModel = viewModel,
                modifier = androidx.compose.ui.Modifier
            )
        }

        verify(viewModel).loadUser(userId)
    }
} 