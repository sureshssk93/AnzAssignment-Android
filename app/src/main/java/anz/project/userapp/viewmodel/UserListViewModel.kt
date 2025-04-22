package anz.project.userapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anz.project.domain.model.User
import anz.project.domain.usecase.GetUsersUseCase
import anz.project.userapp.common.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _users = MutableStateFlow<UserListState>(UserListState.Loading)
    val users: StateFlow<UserListState> = _users

    init {
        loadUsers()
    }


    private fun loadUsers() {
        viewModelScope.launch {
            _users.value = UserListState.Loading
            try {
                val users = getUsersUseCase()
                _users.value = UserListState.Success(users)
            } catch (e: Exception) {
                _users.value = UserListState.Error(e.message ?: AppConstants.Errors.UNKNOWN_ERROR)
            }
        }
    }
    sealed class UserListState {
        object Loading : UserListState()
        data class Success(val users: List<User>) : UserListState()
        data class Error(val message: String) : UserListState()
    }
}