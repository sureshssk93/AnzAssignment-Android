package anz.project.userapp.ui.list

import anz.project.domain.model.User
import anz.project.userapp.common.AppConstants
import anz.project.userapp.ui.common.CoilImageFromUrl
import anz.project.userapp.viewmodel.UserListViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Preview
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel(),
    onUserClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val usersState = viewModel.users.collectAsState()

    when (val state = usersState.value) {
        is UserListViewModel.UserListState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is UserListViewModel.UserListState.Success -> {
            UserList(users = state.users, onUserClick = onUserClick, modifier = modifier)
        }

        is UserListViewModel.UserListState.Error -> {
            Text(
                text = state.message,
                modifier = modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview
@Composable
fun UserList(users: List<User>, onUserClick: (Int) -> Unit, modifier: Modifier = Modifier) {

    val stableOnUserClick = remember(onUserClick) {
        { userId: Int -> onUserClick(userId) }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 64.dp, 16.dp, 16.dp)
    ) {
        items(users, key = { user -> user.id }) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { stableOnUserClick(user.id) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CoilImageFromUrl(
                        imageUrl = user.photo,
                        contentDescription = "Photo of ${user.name}",
                        isCircular = true,
                        width = 48,
                        height = 48
                    )
                    
                    Spacer(modifier = modifier.width(16.dp))
                    Column {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = user.country,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}