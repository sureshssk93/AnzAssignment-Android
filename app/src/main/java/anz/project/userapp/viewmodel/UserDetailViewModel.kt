package anz.project.userapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anz.project.domain.model.User
import anz.project.domain.usecase.GetUserDetailUseCase
import anz.project.userapp.common.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<UserState>(UserState.Loading)
    val user: StateFlow<UserState> = _user

    private var currentUserId: Int? = null

    fun loadUser(userId: Int) {
        // Only reload if the userId has changed
        if (currentUserId != userId) {
            currentUserId = userId
            viewModelScope.launch {
                _user.value = UserState.Loading
                try {
                    val user = getUserDetailUseCase(userId)
                    _user.value = UserState.Success(user)
                } catch (e: Exception) {
                    _user.value = UserState.Error(e.message ?: AppConstants.Errors.UNKNOWN_ERROR)
                }
            }
        }
    }

    sealed class UserState {
        object Loading : UserState()
        data class Success(val user: User) : UserState()
        data class Error(val message: String) : UserState()
    }
}